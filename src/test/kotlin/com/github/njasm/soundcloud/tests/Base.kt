package com.github.njasm.soundcloud.tests

import com.github.njasm.soundcloud.Client
import org.junit.Before


open class Base {
    internal lateinit var t : Client

    @Before
    fun init()
    {
        val clientID : String = System.getenv("KOTWAVE_CLIENTID").orEmpty()
        val clientSecret : String = System.getenv("KOTWAVE_CLIENT_SECRET").orEmpty()
        val username : String = System.getenv("KOTWAVE_USERNAME").orEmpty()
        val password : String = System.getenv("KOTWAVE_PASSWORD").orEmpty()

        t = Client(clientID, clientSecret)
        t.clientCredentialsAuthentication(username, password)
    }
}