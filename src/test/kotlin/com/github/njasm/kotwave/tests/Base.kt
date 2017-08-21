package com.github.njasm.kotwave.tests

import com.github.njasm.kotwave.Client
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
        t.setReadTimeout(60000)
        t.clientCredentialsAuthentication(username, password)

        Thread.sleep(2000L)
    }
}