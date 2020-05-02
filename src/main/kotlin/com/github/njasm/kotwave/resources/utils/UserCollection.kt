package com.github.njasm.kotwave.resources.utils

import com.github.njasm.kotwave.resources.User
import com.google.gson.annotations.SerializedName

class UserCollection {

    private lateinit var collection: Array<User>

    @SerializedName("next_href")
    private lateinit var nextHref: String

    fun values(): Array<User> = collection
}
