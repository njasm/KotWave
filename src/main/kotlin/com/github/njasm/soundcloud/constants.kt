/**
 * Created by njasm on 03/07/2017.
 */

package com.github.njasm.soundcloud

// RFC 2616
const val HTTP_HEADER_VALUE_DELIMITER : String = ","

const val API_BASE_URL : String = "https://api.soundcloud.com"
const val API_CONNECT_PATH : String = "connect"
const val API_TOKEN_PATH : String = "oauth2/token"

const val API_ME_RESOURCE : String = "me"
const val API_USERS_RESOURCE : String = "users"
const val API_TRACKS_RESOURCE : String = "tracks"
const val API_PLAYLISTS_RESOURCE : String = "playlists"
const val API_COMMENTS_RESOURCE : String = "comments"
const val API_CONNECTIONS_RESOURCE : String = "me/connections"
const val API_ACTIVITIES_RESOURCE : String = "me/activities"
const val API_APPS_RESOURCE : String = "apps"
const val API_RESOLVE_RESOURCE : String = "resolve"
const val API_OEMBED_RESOURCE : String = "oembed"

const val API_USER_SUB_FOLLOWINGS_RESOURCE : String = "followings"
const val API_USER_SUB_FOLLOWERS_RESOURCE : String = "followers"
const val API_USER_SUB_FAVORITES_RESOURCE : String = "favorites"