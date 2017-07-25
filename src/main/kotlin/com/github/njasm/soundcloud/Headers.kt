/**
 * Created by njasm on 02/07/2017.
 */

package com.github.njasm.soundcloud

internal class Headers {

    private var values : MutableMap<String, String> = HashMap()

    internal fun Add(key : String, value: String) {
        val old = this.values[key]
        if (old == null) {
            this.values.put(key,value)
            return
        }

        val new = old.plus(HTTP_HEADER_VALUE_DELIMITER).plus(value)
        this.values.put(key, new)
    }

    internal fun Remove(key : String) {
        if (this.values.contains(key)) {
            this.values.remove(key)
        }
    }
}