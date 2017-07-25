/**
 * Created by njasm on 02/07/2017.
 */

package com.github.njasm.soundcloud

import com.github.kittinunf.fuel.core.Request
import java.util.*

internal class Auth
{
    internal var token : Token
    internal var dataDate : Calendar

    val accessToken
        get() = this.token.access_token

    val tokenScope
        get() = this.token.scope

    val refreshToken
        get() = this.token.refresh_token

    constructor(token : Token, date : Calendar = Calendar.getInstance()) {
        this.token = token
        this.dataDate = date
    }

    fun addOauthHeader(req: Request) {
        if (this.token.access_token.isNotEmpty() && !req.httpHeaders.containsKey("Authorization")) {
            req.header("Authorization" to "OAuth " + this.token.access_token.trim())
        }
    }

    fun isTokenExpired() : Boolean {
        var result = false
        with (this) {
            val expires = getExpiresIn()
            when(expires) {
                null -> return result
                else -> {
                    this.dataDate.add(Calendar.SECOND, expires!!)
                    result = this.dataDate >= Calendar.getInstance()
                }
            }
        }

        return result
    }

    private fun getExpiresIn() : Int? = this.token?.expires_in
}

internal data class Token(
        val access_token : String = "",
        val scope : String = "",
        val expires_in : Int? = null,
        val refresh_token : String? = null)
