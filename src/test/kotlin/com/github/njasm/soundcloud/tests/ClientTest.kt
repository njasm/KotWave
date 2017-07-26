/**
 * Created by njasm on 04/07/2017.
 */

package com.github.njasm.soundcloud.tests

import com.github.kittinunf.result.Result
import com.github.njasm.soundcloud.API_BASE_URL
import com.github.njasm.soundcloud.API_ME_RESOURCE
import com.github.njasm.soundcloud.pathCombine
import org.junit.Ignore
import org.junit.Test

class ClientTest : Base() {

    @Test
    fun clientCredentialsAuthentication()
    {
        assert(t.auth.accessToken.isNotEmpty())
    }

    @Test
    fun getMe()
    {
        val user = t.me()
        println(user.client.auth.accessToken)
        println(user.id)
        println(user.permalink)
        println(user.username)
        println(user.uri)

        assert(user.client.auth.accessToken.isNotEmpty())
    }

    @Test
    fun tracksOf()
    {
        val tracks = t.tracksOf(t.me().id)
        tracks.forEach { println("name: ${it.title}") }

        assert(tracks.isNotEmpty())
    }

    @Test
    fun commentsOf()
    {
        val comments = t.commentsOf(t.me().id)
        comments.forEach { println("name: ${it.body}") }

        assert(comments.isNotEmpty())
    }

    @Ignore("not yet ready")
    @Test
    fun resolve()
    {
        val value = t.resolve("https://soundcloud.com/hybrid-species")
        assert(value != null)
    }


    @Test
    fun getHead()
    {
        val (_, response, r) = t.head(API_BASE_URL.pathCombine(API_ME_RESOURCE))
        when(r) {
            is Result.Failure -> assert(false)
            else -> {
                response.httpResponseHeaders.forEach {
                    println("key: ${it.key}, value: ${it.value}")
                }

                assert(response.httpResponseHeaders.isNotEmpty())
            }
        }
    }
}