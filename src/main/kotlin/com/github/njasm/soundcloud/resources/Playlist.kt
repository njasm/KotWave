package com.github.njasm.soundcloud.resources

import com.google.gson.annotations.SerializedName

class Playlist : Resource() {

    val id : Int = 0
    @SerializedName("created_at")
    lateinit var createdAt : String

    @SerializedName("user_id")
    var userId : Int = 0
    var duration : Int = 0

    var sharing : String = ""
    @SerializedName("tag_list")
    lateinit var tagList : String
    lateinit var permalink : String

    @SerializedName("track_count")
    var trackCount : Int = 0

    var streamable : Boolean = false
    var downloadable : Boolean = false
    @SerializedName("embeddable_by")
    var embeddableBy : String = ""
    @SerializedName("purchase_url")
    var purchaseUrl : String? = null
    @SerializedName("label_id")
    var labelId : Int? = null
    var type : String = ""
    @SerializedName("playlist_type")
    var playlistType : String = ""
    var ean : String = ""
    var description : String = ""
    var genre : String = ""
    var release : String = ""
    @SerializedName("purchase_title")
    var purchaseTitle : String? = null
    @SerializedName("label_name")
    var labelName : String = ""
    var title : String = ""
    @SerializedName("release_year")
    var releaseYear : String? = null
    @SerializedName("release_month")
    var releaseMonth : String? = null
    @SerializedName("release_day")
    var releaseDay : String? = null
    var license : String = ""
    var uri : String = ""
    @SerializedName("permalink_url")
    var permalinkUrl : String = ""
    @SerializedName("artwork_url")
    var artworkUrl : String = ""

    var user : User = User()
    var tracks : Array<Track> = emptyArray()

    override fun save() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

