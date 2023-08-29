package com.bagusmerta.utility.extensions

import android.graphics.Color

fun Int.changeBackgroundColorAppBar(fraction: Double): Int {
    val red: Int = Color.red(this)
    val green: Int = Color.green(this)
    val blue: Int = Color.blue(this)
    val alpha: Int = (Color.alpha(this) * fraction).toInt()
    return Color.argb(alpha, red, green, blue)
}