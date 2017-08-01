/**
 * Created by njasm on 02/07/2017.
 */

package com.github.njasm.soundcloud

import com.github.kittinunf.fuel.core.Request
import java.util.*

internal class Auth(internal var token: Token) {
    internal var dataDate : Calendar = Calendar.getInstance()

    val accessToken
        get() = this.token.access_token

    val tokenScope
        get() = this.token.scope

    val refreshToken
        get() = this.token.refresh_token

    fun addOauthHeader(req: Request)
    {
        if (tokenIsNotEmpty()
            && !req.httpHeaders.containsKey("Authorization")
            && !isTokenExpired())
        {
            req.header("Authorization" to "OAuth " + this.accessToken.trim())
        }
    }

    fun isTokenExpired() : Boolean
    {
        when(this.getExpiresIn()) {
            null -> return false
            else -> {
                dataDate.add(Calendar.SECOND, getExpiresIn()!!)
                return dataDate.compareTo(Calendar.getInstance()) <= 0
            }
        }
    }

    private fun getExpiresIn() : Int? = this.token.expires_in
    private fun tokenIsNotEmpty() : Boolean = this.accessToken.isNotEmpty()

}

internal data class Token(
        val access_token : String = "",
        val scope : String = "",
        val expires_in : Int? = null,
        val refresh_token : String? = null)
