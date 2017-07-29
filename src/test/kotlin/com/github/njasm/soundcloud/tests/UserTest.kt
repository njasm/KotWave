package com.github.njasm.soundcloud.tests

import org.junit.Ignore
import org.junit.Test
import org.junit.Assert

class UserTest : Base() {

    @Test
    fun tracks()
    {
        val tracks = t.me().tracks()
        tracks.forEach { println("name: ${it.title}") }

        Assert.assertTrue(tracks.isNotEmpty())
    }

    @Test
    fun comments()
    {
        val comments = t.me().comments()
        comments.forEach { println("name: ${it.body}") }

        Assert.assertTrue(comments.isNotEmpty())
    }

    @Test
    fun followings()
    {
        val followings = t.me().followings()
        followings.forEach { println("name: ${it.fullName}") }

        Assert.assertTrue(followings.isNotEmpty())
    }

    @Ignore("FIXME: Intermittent errors 401 - Unauthorized")
    @Test
    fun followers()
    {
        val followers = t.me().followers()
        followers.forEach { println("name: ${it.fullName}") }

        Assert.assertTrue(followers.isNotEmpty())
    }

    @Test
    fun favorites()
    {
        val favs = t.me().favorites()
        favs.forEach { println("name: ${it.title}") }

        Assert.assertTrue(true)
    }

    @Ignore("FIXME: Intermittent errors 401 - Unauthorized")
    @Test
    fun apps()
    {
        val apps = t.me().apps()
        apps.forEach { println("name: ${it.name}") }

        Assert.assertTrue(true)
    }

    @Ignore("not yet ready")
    @Test
    fun connections()
    {
        val connections = t.me().connections()
        connections.forEach { println("name: ${it.displayName}") }

        Assert.assertTrue(true)
    }
}