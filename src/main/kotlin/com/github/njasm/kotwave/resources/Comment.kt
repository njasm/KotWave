package com.github.njasm.kotwave.resources

import com.google.gson.annotations.SerializedName

class Comment : Resource() {
    internal val id: Int = 0        //id 	RO 	integer ID 	123
    lateinit var uri: String        // 	RO 	API resource URL 	"http://api.soundcloud.com/comments/32562"
    @SerializedName("created_at")
    lateinit var createdAt: String //created_at 	RO 	timestamp of creation 	"2009/08/13 18:30:10 +0000"
    lateinit var body: String      //body 	RW 	HTML comment body 	"i love this beat!"
    var timestamp: Int = 0        //	RW 	associated timestamp in milliseconds 	55593
    @SerializedName("user_id")
    var userId: Int = 0            //user_id 	RO 	user id of the owner 	343
    lateinit var user: User        //	RO 	mini user representation of the owner 	{id: 343, username: "Doctor Wilson"...}
    @SerializedName("track_id")
    var trackId: Int = 0           // track_id 	RO 	the track id of the related track 	54

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