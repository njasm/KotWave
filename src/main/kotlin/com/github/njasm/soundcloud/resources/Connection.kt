package com.github.njasm.soundcloud.resources

import com.google.gson.annotations.SerializedName
import com.sun.org.apache.xpath.internal.operations.Bool

class Connection : Resource() {

    var id : Int = 0
    @SerializedName("created_at")
    lateinit var createdAt : String
    @SerializedName("display_name")
    lateinit var displayName : String
    @SerializedName("post_favorite")
    var postFavorite : Boolean = false
    @SerializedName("post_publish")
    var postPublish : Boolean = false
    lateinit var service : String
    lateinit var type : String
    var uri : String = ""

    override fun save() {
        TODO("not implemented")
    }

    override fun update() {
        throw UnsupportedOperationException("")
    }

    override fun delete() {
        throw UnsupportedOperationException("")
    }
}

/*
    "created_at": "2010/12/05 16:46:34 +0000",
    "display_name": "a facebook artist page",
    "id": 313104,
    "post_favorite": false,
    "post_publish": false,
    "service": "facebook_page",
    "type": "facebook_page",
    "uri": "https://api.soundcloud.com/connections/313104"
 */