/**
 * Created by njasm on 02/07/2017.
 */

package com.github.njasm.soundcloud

import com.github.njasm.soundcloud.resources.*

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpHead
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

class Client(val clientID: String, val secret: String, val callback: String = "") {

    internal val auth : Auth = Auth(Token())

    internal lateinit var lastRequest : Request
    internal lateinit var lastResponse : Response

    constructor(clientID: String, secret: String) : this(clientID, secret, "")

    fun clientCredentialsAuthentication(user: String, passwd: String) {
        val body = mutableSetOf(
                "grant_type" to "password",
                "scope" to "*",// "non-expiring",
                "username" to user,
                "password" to passwd,
                "client_id" to this.clientID,
                "client_secret" to this.secret)

        val header = mutableSetOf("Content-Type" to "application/x-www-form-urlencoded")
        val (_, _, result) = this.post(API_BASE_URL.pathCombine(API_TOKEN_PATH), body, header)

        val newToken = processResponseString(result, Token::class.java)
        auth.token = newToken
    }

    fun refreshAccessToken(refreshToken : String?) {
//        val body = mutableSetOf(
//                "grant_type" to "refresh_token",
//                "redirect_uri" to callback,
//                "client_id" to clientID,
//                "client_secret" to secret,
//                "refresh_token" to (if (refreshToken != null) refreshToken!! else auth.refreshToken!!))
//
//        val header = mutableSetOf("Content-Type" to "application/x-www-form-urlencoded")
//        val (request, response, result) = this.post(API_BASE_URL.pathCombine(API_TOKEN_PATH), body, header)
//
//        when (result) {
//            is Result.Failure -> throw result.error
//            is Result.Success -> {
//                lastRequest = request
//                lastResponse = response
//                println(response.data.toString(Charsets.UTF_8))
//            }
//        }
    }

    fun me() : User {
        val (_, _, result) = this.get(API_BASE_URL.pathCombine(API_ME_RESOURCE))
        val u = processResponseString(result, User::class.java)
        u.client = this

        return u
    }

    @JvmOverloads
    fun get(url: String, params: MutableSet<Pair<String, Any>>? = null, headers: MutableSet<Pair<String, String>>? = null
    ) : Triple<Request, Response, Result<String, FuelError>>
    {
        val req = url.httpGet(params?.toList())
        req.header("Accept" to "application/json")
        headers?.forEach { req.header(it) }
        auth.addOauthHeader(req)

        return doRequest(req)
    }

    @JvmOverloads
    fun post(url: String, params: MutableSet<Pair<String, String>>? = null, headers: MutableSet<Pair<String, String>>? = null
    ) : Triple<Request, Response, Result<String, FuelError>>
    {
        val req = url.httpPost()
        headers?.forEach { req.header(it) }
        auth.addOauthHeader(req)

        val strBody = params.getBodyOr("")
        req.body(strBody, Charsets.UTF_8)

        return doRequest(req)
    }

    @JvmOverloads
    fun head(url: String, headers: MutableSet<Pair<String, String>>? = null) : Triple<Request, Response, Result<String, FuelError>>
        = url.httpHead().let { req ->
            headers?.forEach { h -> req.header(h) }
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
        val fullUrl = API_BASE_URL.pathCombine(API_USERS_RESOURCE, userId, API_USER_SUB_CONNECTIONS_RESOURCE)
        val (_, _, result) = this.get(fullUrl)
        val c = processResponseString(result, Array<Connection>::class.java)
        c.forEach { it.client = this }

        return c
    }

    fun resolve(uri: String) : String?
    {
        val fullUrl = API_BASE_URL.pathCombine(API_RESOLVE_RESOURCE)
        val (_, response, result) = this.get(fullUrl, mutableSetOf("url" to uri, "client_id" to this.clientID))
        return when(result) {
            is Result.Success ->
                if (response.httpResponseHeaders.containsKey("Location"))
                    response.httpResponseHeaders["Location"]!!.first()
                else null
            else -> null
        }
    }

    private inline fun <reified T : Any, V : Any, E : Exception> processResponseString(result: Result<V, E>,
                                                                                 returnType: Class<T>) : T
    {
        return when (result) {
            is Result.Failure -> throw result.error
            is Result.Success -> fromJson(result.value.toString().toByteArray(), returnType)
        }
    }

}
