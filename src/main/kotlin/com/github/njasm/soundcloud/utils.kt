/**
 * Created by njasm on 06/07/2017.
 */

package com.github.njasm.soundcloud

import com.google.gson.Gson
import java.nio.charset.Charset

fun getBody(params: MutableSet<Pair<String, String>>) : String {
    val strBody = params.fold(String(),
            { acc : String, (first, second) ->
                acc.plus(first).plus("=").plus(second).plus("&") })

    return strBody.substring(0, strBody.length - 1)
}

fun MutableSet<Pair<String, String>>?.getBodyOr(value : String)
        = if (this != null && this.count() > 0) getBody(this) else value

fun String.pathCombine(vararg others : Any) : String {
    val combiner = "/"
    var result = this.removeSuffix(combiner)
    others.forEach { result = result.plus(combiner).plus(it.toString()) }

    return result
}

@JvmOverloads
inline internal fun <reified T : RuntimeException> throwIf(
        message : String = "Condition is true.",
        action : () -> Boolean)
{
    if (action.invoke())
        throw T::class.java.getConstructor().newInstance(message)
}

@JvmOverloads
internal fun <T> fromJson(data : ByteArray, type : Class<T>, charset : Charset = Charsets.UTF_8) : T {
    val jsonResponse = data.toString(charset)
    return Gson().fromJson(jsonResponse, type)
}
