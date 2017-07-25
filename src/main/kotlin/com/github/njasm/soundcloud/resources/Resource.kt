package com.github.njasm.soundcloud.resources

/**
 * Created by njasm on 07/07/2017.
 */

import com.github.njasm.soundcloud.Client

abstract class Resource {
    internal var kind : String = ""

    internal lateinit var client : Client

    abstract fun save()
    abstract fun update()
    abstract fun delete()
}