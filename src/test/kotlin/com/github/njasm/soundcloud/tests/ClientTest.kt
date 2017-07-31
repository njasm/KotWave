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
import org.junit.Assert

class ClientTest : Base() {

    @Test
    fun clientCredentialsAuthentication()
    {
        Assert.assertTrue(t.auth.accessToken.isNotEmpty())
    }

    @Test
    fun refreshToken()
    {
        val token = t.auth.accessToken
        val rToken = t.auth.refreshToken

        t.refreshAccessToken(rToken)

        Assert.assertNotEquals(token, t.auth.accessToken)
        Assert.assertNotEquals(rToken, t.auth.refreshToken)

        val nToken = t.auth.accessToken
        val nrToken = t.auth.refreshToken

        t.refreshAccessToken(null)
        Assert.assertNotEquals(nToken, t.auth.accessToken)
        Assert.assertNotEquals(nrToken, t.auth.refreshToken)

        Assert.assertNotEquals(token, nToken)
        Assert.assertNotEquals(rToken, nrToken)

        Thread.sleep(2_000)
    }

    @Test
    fun factoryMethods()
    {
        val track = t.factoryTrack()
        val conn = t.factoryConnection()
        val com = t.factoryComment()

        Assert.assertNotNull(track)
        Assert.assertNotNull(conn)
        Assert.assertNotNull(com)

        Assert.assertEquals(track.client, t)
        Assert.assertEquals(conn.client, t)
        Assert.assertEquals(com.client, t)
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

        Assert.assertTrue(user.client.auth.accessToken.isNotEmpty())
    }

    @Test
    fun tracksOf()
    {
        val tracks = t.tracksOf(t.me().id)
        tracks.forEach { println("name: ${it.title}") }

        Assert.assertTrue(tracks.isNotEmpty())
    }

    @Test
    fun commentsOf()
    {
        val comments = t.commentsOf(t.me().id)
        comments.forEach { println("name: ${it.body}") }

        Assert.assertTrue(comments.isNotEmpty())
    }

    @Ignore("not yet ready")
    @Test
    fun resolve()
    {
        val value = t.resolve("https://soundcloud.com/hybrid-species")
        Assert.assertNotNull(value)
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

                Assert.assertTrue(response.httpResponseHeaders.isNotEmpty())
            }
        }
    }
}