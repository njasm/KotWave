package com.github.njasm.soundcloud.resources

import com.google.gson.annotations.SerializedName

/**
 * Created by njasm on 16/07/2017.
 */
class Track : Resource() {
    val id : Int = 0    	            // RO 	integer ID 	123
    @SerializedName("created_at")
    lateinit var createdAt : String 	// RO 	timestamp of creation 	"2009/08/13 18:30:10 +0000"
    @SerializedName("user_id")
    var userId : Int = 0	            // RO 	user-id of the owner 	343
    lateinit var user : User            // RO 	mini user representation of the owner 	{id: 343, username: "Doctor Wilson"...}
    lateinit var title : String     	// RW 	track title 	"S-Bahn Sounds"

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