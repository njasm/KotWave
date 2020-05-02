package com.github.njasm.kotwave.resources

import com.google.gson.annotations.SerializedName

class App : Resource() {

    var id: Int = 0
    lateinit var name: String
    lateinit var uri: String

    @SerializedName("permalink_url")
    lateinit var permalinkUrl: String

    @SerializedName("external_url")
    lateinit var externalUrl: String
    lateinit var creator: String

    override fun save() {
        throw UnsupportedOperationException("You cannot save an App resource")
    }

    override fun update() {
        throw UnsupportedOperationException("You cannot update an App resource")
    }

    override fun delete() {
        throw UnsupportedOperationException("You cannot delete an App resource")
    }

}

/*
 "id": 124,
  "kind": "app",
  "name": "SoundCloud iOS",
  "uri": "https://api.soundcloud.com/apps/124",
  "permalink_url": "http://soundcloud.com/apps/iphone",
  "external_url": "http://itunes.com/app/soundcloud",
  "creator": "kotwave"
 */