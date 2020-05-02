/**
 * Created by njasm on 03/07/2017.
 */

package com.github.njasm.kotwave.tests

import com.github.njasm.kotwave.Auth
import com.github.njasm.kotwave.Token
import org.junit.Before
import org.junit.Test
import org.junit.Assert

class AuthTest {

    internal lateinit var t: Auth

    @Before
    fun init() {
        t = Auth(Token())
    }

    @Test
    fun scopeIsEmpty() = Assert.assertEquals(t.tokenScope, "")

    @Test
    fun tokenExpiredFalse() = Assert.assertFalse(t.isTokenExpired())

    @Test
    fun tokenExpiredTrue() {
        val rt = "r"
        var a = Auth(Token("a", "*", 1, rt))
        Thread.sleep(2000)

        Assert.assertTrue(a.isTokenExpired())
        Assert.assertEquals(rt, a.refreshToken!!)
    }
}