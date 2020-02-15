/**
 * Created by njasm on 02/07/2017.
 */

package com.github.njasm.kotwave

import com.github.njasm.kotwave.resources.*

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpHead
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut
import com.github.kittinunf.result.Result
import com.github.njasm.kotwave.resources.utils.UserCollection

class Client(val clientID: String, val secret: String, val callback: String = "") {

    internal val auth : Auth = Auth(Token())

    internal lateinit var lastRequest : Request
    internal lateinit var lastResponse : Response

    internal var defaultReadTimeout : Int = 60000

    constructor(clientID: String, secret: String) : this(clientID, secret, "")

    fun setReadTimeout(milliseconds : Int) {
        defaultReadTimeout = milliseconds
    }

    fun clientCredentialsAuthentication(user: String, passwd: String)
    {
        val body = setOf(
                "grant_type" to "password",
                "scope" to "non-expiring", // "*",
                "username" to user,
                "password" to passwd,
                "client_id" to this.clientID,
                "client_secret" to this.secret)

        val header = setOf("Content-Type" to "application/x-www-form-urlencoded")
        val (_, _, result) = this.post(API_BASE_URL.pathCombine(API_TOKEN_PATH), body, header)

        val newToken = processResponseString(result, Token::class.java)
        auth.token = newToken
    }

    fun refreshAccessToken(refreshToken : String?)
    {
        val body = setOf(
                "grant_type" to "refresh_token",
                "redirect_uri" to callback,
                "client_id" to clientID,
                "client_secret" to secret,
                "refresh_token" to (refreshToken ?: auth.refreshToken.orEmpty()))

        val header = setOf("Content-Type" to "application/x-www-form-urlencoded")
        val (_, _, result) = this.post(API_BASE_URL.pathCombine(API_TOKEN_PATH), body, header)

        val newToken = processResponseString(result, Token::class.java)
        auth.token = newToken
    }

    fun me() : User
    {
        val (_, _, result) = this.get(API_BASE_URL.pathCombine(API_ME_RESOURCE))
        val u = processResponseString(result, User::class.java)
        u.client = this

        return u
    }

    @JvmOverloads
    fun get(url: String, params: Set<Pair<String, Any>> = emptySet(), headers: Set<Pair<String, Any>> = emptySet()
    ) : Triple<Request, Response, Result<String, FuelError>>
    {
        val req = url.httpGet(params.toList()).timeoutRead(defaultReadTimeout)
        req.apply {
            header("Accept" to "application/json")
            headers.forEach { header(it) }
        }

        guardAgainstExpiredToken()
        auth.addOauthHeader(req)

        return doRequest(req)
    }

    fun post(url: String, params: Set<Pair<String, Any>> = emptySet(), headers: Set<Pair<String, Any>> = emptySet()
    ) : Triple<Request, Response, Result<String, FuelError>>
    {
        val req = url.httpPost().timeoutRead(defaultReadTimeout)
        headers.forEach { req.header(it) }
        guardAgainstExpiredToken()
        auth.addOauthHeader(req)

        val strBody = params.getBodyOr("")
        req.body(strBody, Charsets.UTF_8)

        return doRequest(req)
    }

    fun put(url: String, params: Set<Pair<String, Any>> = emptySet(), headers: Set<Pair<String, Any>> = emptySet()
    ) : Triple<Request, Response, Result<String, FuelError>>
    {
        val req = url.httpPut().timeoutRead(defaultReadTimeout)
        headers.forEach { req.header(it) }
        guardAgainstExpiredToken()
        auth.addOauthHeader(req)

        val strBody = params.getBodyOr("")
        req.body(strBody, Charsets.UTF_8)

        return doRequest(req)
    }

    fun head(url: String, headers: Set<Pair<String, Any>> = emptySet()
    ) : Triple<Request, Response, Result<String, FuelError>> = url.httpHead().let { req ->
            req.timeoutRead(defaultReadTimeout)
            headers.forEach { h -> req.header(h) }
            guardAgainstExpiredToken()
            auth.addOauthHeader(req)

            return doRequest(req)
        }

    private fun doRequest(req: Request) : Triple<Request, Response, Result<String, FuelError>>
        = with(req.responseString()) {
            lastRequest = first
            lastResponse = second

            return this
        }

    fun tracksOf(userId: Int) : Array<Track>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_TRACKS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val tracks = processResponseString(result, Array<Track>::class.java)
        tracks.forEach { track: Track -> track.client = this }

        return tracks
    }

    fun commentsOf(userId: Int) : Array<Comment>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_COMMENTS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val comments = processResponseString(result, Array<Comment>::class.java)
        comments.forEach { comment: Comment -> comment.client = this }

        return comments
    }

    fun followingsOf(userId: Int) : Array<User>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_USER_SUB_FOLLOWINGS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val users = processResponseString(result, UserCollection::class.java)
        users.values().forEach { it.client = this }

        return users.values()
    }

    fun followersOf(userId: Int) : Array<User>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_USER_SUB_FOLLOWERS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val users = processResponseString(result, UserCollection::class.java)
        users.values().forEach { it.client = this }

        return users.values()
    }

    fun favoritesOf(userId: Int) : Array<Track>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_USER_SUB_FAVORITES_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val t = processResponseString(result, Array<Track>::class.java)
        t.forEach { it.client = this }

        return t
    }

    fun connectionsOf(userId: Int) : Array<Connection>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_CONNECTIONS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val c = processResponseString(result, Array<Connection>::class.java)
        c.forEach { it.client = this }

        return c
    }

    fun appsOf(userId : Int) : Array<App>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val fullUrl = API_BASE_URL.pathCombine(API_APPS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val a = processResponseString(result, Array<App>::class.java)
        a.forEach { it.client = this }

        return a
    }

    fun playlistsOf(userId : Int) : Array<Playlist>
    {
        throwIf<IllegalArgumentException>("User ID cannot be negative or zero.") { userId <= 0 }
        val url = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_PLAYLISTS_RESOURCE)
        val (_, _, result) = get(url)

        val pList = processResponseString(result, Array<Playlist>::class.java)
        pList.forEach { p ->
            p.client = this
            p.user.client = this
            p.tracks().forEach { t -> t.client = this }
        }

        return pList
    }

    fun playlistOf(playlistId : Int) : Playlist?
    {
        throwIf<IllegalArgumentException>("Playlist ID cannot be negative or zero.") { playlistId <= 0 }
        val url = API_BASE_URL.pathCombine(API_PLAYLISTS_RESOURCE, playlistId)
        val (_, _, result) = get(url)

        val p = processResponseString(result, Playlist::class.java)
        p.let { it.client = this }

        return p
    }

    fun resolve(uri: String) : String?
    {
        val fullUrl = API_BASE_URL.pathCombine(API_RESOLVE_RESOURCE)
        val (_, response, result) = this.get(fullUrl, mutableSetOf("url" to uri, "client_id" to this.clientID))
        return when(result) {
            is Result.Success -> response.headers["Location"]?.first()
            else -> null
        }
    }

    fun factoryTrack() : Track = (Track()).let { it.client = this; return it }
    fun factoryComment() : Comment = (Comment()).let { it.client = this; return it }
    fun factoryConnection() : Comment = (Comment()).let { it.client = this; return it }

    private inline fun <reified T : Any, V : Any, E : Exception>
            processResponseString(result: Result<V, E>, returnType: Class<T>) : T
    {
        return when (result) {
            is Result.Failure -> {
                println("ERROR HTTP RESPONSE BODY: ${lastResponse.data.toString(Charsets.UTF_8)}")
                throw result.error
            }
            is Result.Success -> fromJson(result.value.toString().toByteArray(), returnType)
        }
    }

    private fun guardAgainstExpiredToken() =
            if (auth.isTokenExpired()) refreshAccessToken(null) else Unit
}
