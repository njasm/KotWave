/**
 * Created by njasm on 03/07/2017.
 */

package com.github.njasm.soundcloud.tests

import com.github.njasm.soundcloud.Auth
import com.github.njasm.soundcloud.Token
import org.junit.Before
import org.junit.Test
import java.util.*

class AuthTest {

    internal lateinit var t : Auth

    @Before
    fun init()
    {
        t = Auth(Token())
    }

    @Test
    fun scopeIsEmpty() = assert(t.tokenScope == "")

    @Test
    fun tokenExpiredFalse() = assert(!t.isTokenExpired())

    @Test
    fun tokenExpiredTrue()
    {
        val rt = "r"
        var a = Auth(Token("a", "*", 1, rt))
        Thread.sleep(2000)

        assert(a.isTokenExpired())
        assert(rt == a.refreshToken!!)
    }
}