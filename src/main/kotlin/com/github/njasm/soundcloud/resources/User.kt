package com.github.njasm.soundcloud.resources

import com.github.kittinunf.result.Result
import com.github.njasm.soundcloud.*
import com.google.gson.annotations.SerializedName

class User : Resource() {

    val id : Int = 0
    lateinit var permalink : String
    lateinit var username : String
    lateinit var uri : String
    @SerializedName("permalink_url")
    lateinit var permalinkUrl : String             // RO 	URL to the SoundCloud.com page 	"http://soundcloud.com/bryan/sbahn-sounds"
    @SerializedName("avatar_url")
    lateinit var avatarUrl : String                // RO 	URL to a JPEG image 	"http://i1.sndcdn.com/avatars-000011353294-n0axp1-large.jpg"
    lateinit var country : String                   // RO 	country 	"Germany"
    @SerializedName("full_name")
    lateinit var fullName : String                 // RO 	first and last name 	"Tom Wilson"
    lateinit var city : String                      // RO 	city 	"Berlin"
    lateinit var description : String 	            // RW 	description 	"Buskers playing in the S-Bahn station in Berlin"
    @SerializedName("discogs-name")
    lateinit var discogsName : String	            // RO 	Discogs name 	"myrandomband"
    @SerializedName("myspace-name")
    lateinit var myspaceName : String              //	RO 	MySpace name 	"myrandomband"
    lateinit var website : String                   //	RW 	a URL to the website 	"http://facebook.com/myrandomband"
    @SerializedName("website-title")
    lateinit var websiteTitle : String             // 	RW 	a custom title for the website 	"myrandomband on Facebook"
    lateinit var online : String                    // 	RO 	online status (boolean) 	true
    @SerializedName("track_count")
    lateinit var trackCount : String               // 	RO 	number of public tracks 	4
    @SerializedName("playlist_count")
    lateinit var playlistCount : String            // 	RO 	number of public playlists 	5
    @SerializedName("followers_count")
    lateinit var followersCount : String           // 	RO 	number of followers 	54
    @SerializedName("followings_count")
    lateinit var followingsCount : String          // 	RO 	number of followed users 	75
    @SerializedName("public_favorites_count")
    lateinit var publicFavoritesCount : String    // 	RO 	number of favorited public tracks 	7
    @SerializedName("avatar_data")
    lateinit var avatarData : String               // 	WO 	binary data of user avatar 	(only for uploading)

    override fun save() = throw UnsupportedOperationException("")
    override fun delete() = throw UnsupportedOperationException("")
    override fun update() = Unit

    fun tracks() : Array<Track> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.tracksOf(this.id)
    }

    fun comments() : Array<Comment> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.commentsOf(this.id)
    }

    fun followings() : Array<User> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.followingsOf(this.id)
    }

    fun followers() : Array<User> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.followersOf(this.id)
    }

    fun follow(userId : Int)
    {
        throwIf<IllegalArgumentException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        throwIf<IllegalArgumentException>("userId is not valid.") {userId <= 0}
        val url = API_BASE_URL.pathCombine(API_USERS_RESOURCE, this.id, API_USER_SUB_FOLLOWERS_RESOURCE, userId)
        val (_, _, result) = this.client.put(url)
        when(result) {
            is Result.Failure -> throw result.error
            else -> return Unit
        }
    }

//    fun unfollow(userId : Int)
//    {
//        throwIf<IllegalArgumentException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
//        throwIf<IllegalArgumentException>("userId is not valid.") {userId <= 0}
//        val url = API_BASE_URL.pathCombine(API_USERS_RESOURCE, this.id, API_USER_SUB_FOLLOWERS_RESOURCE, userId)
//        val (_, _, result) = this.client.delete(url)
//        when(result) {
//            is Result.Failure -> throw result.error
//        }
//    }
//
//    fun unfollow(user : User)
//    {
//
//    }

    fun favorites() : Array<Track> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.favoritesOf(this.id)
    }

    fun connections() : Array<Connection> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.connectionsOf(this.id)
    }

    fun apps() : Array<App> {
        throwIf<IllegalStateException>("${this.javaClass.name} is not loaded.") {this.id <= 0}
        return this.client.appsOf(this.id)
    }

    fun playlists() = Unit
}

/*
GET 	/users/{id} 	a user
GET 	/users/{id}/tracks 	list of tracks of the user
GET 	/users/{id}/playlists 	list of playlists (sets) of the user
GET 	/users/{id}/followings 	list of users who are followed by the user
GET, PUT, DELETE 	/users/{id}/followings/{id} 	a user who is followed by the user
GET 	/users/{id}/followers 	list of users who are following the user
GET 	/users/{id}/followers/{id} 	user who is following the user
GET 	/users/{id}/comments 	list of comments from this user
GET 	/users/{id}/favorites 	list of tracks favorited by the user
GET, PUT, DELETE 	/users/{id}/favorites/{id} 	track favorited by the user
GET, PUT, DELETE 	/users/{id}/web-profiles 	list of web profiles
 */