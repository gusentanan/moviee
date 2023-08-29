package com.bagusmerta.utility.extensions

import java.lang.StringBuilder
import kotlin.random.Random

fun <T> MutableList<T>.findRandom(): T? {
    if(isEmpty()) return null
    val index = Random.nextInt(size)
    return this[index]
}

fun <T> Iterable<T>.joinToGenreString(
    separator: (Int) -> CharSequence = { " • " }
): String {
    return joinTo(StringBuilder(), separator).toString()
}

private fun <T, A : Appendable> Iterable<T>.joinTo(
    buffer: A,
    separator: (Int) -> CharSequence
): A {
    val limit = -1
    var count = 0
    for (element in this) {
        if (++count > 1) buffer.append(separator(count - 1))
        if (limit < 0 || count <= limit) {
            buffer.append(element.toString())
        } else break
    }
    return buffer
}