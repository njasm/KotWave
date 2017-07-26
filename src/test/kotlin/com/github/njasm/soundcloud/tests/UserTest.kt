package com.github.njasm.soundcloud.tests

import org.junit.Ignore
import org.junit.Test

class UserTest : Base() {

    @Test
    fun tracks()
    {
        val tracks = t.me().tracks()
        tracks.forEach { println("name: ${it.title}") }

        assert(tracks.isNotEmpty())
    }

    @Test
    fun comments()
    {
        val comments = t.me().comments()
        comments.forEach { println("name: ${it.body}") }

        assert(comments.isNotEmpty())
    }

    @Test
    fun followings()
    {
        val followings = t.me().followings()
        followings.forEach { println("name: ${it.fullName}") }

        assert(followings.isNotEmpty())
    }

    @Test
    fun followers()
    {
        val followers = t.me().followers()
        followers.forEach { println("name: ${it.fullName}") }

        assert(followers.isNotEmpty())
    }

    @Test
    fun favorites()
    {
        val favs = t.me().favorites()
        favs.forEach { println("name: ${it.title}") }

        assert(true)
    }

    @Ignore("not yet ready")
    @Test
    fun connections()
    {
        val connections = t.me().connections()
        connections.forEach { println("name: ${it.displayName}") }

        assert(true)
    }
}