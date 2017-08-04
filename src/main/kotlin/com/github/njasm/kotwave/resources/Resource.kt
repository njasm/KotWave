package com.github.njasm.kotwave.resources

/**
 * Created by njasm on 07/07/2017.
 */

import com.github.njasm.kotwave.Client

abstract class Resource {
    internal var kind : String = ""

    internal lateinit var client : Client

    abstract fun save()
    abstract fun update()
    abstract fun delete()
}