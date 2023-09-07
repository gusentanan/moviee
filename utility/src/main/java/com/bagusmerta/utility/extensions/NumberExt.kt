package com.bagusmerta.utility.extensions


fun Double.toPercentageString(): String {
    val percentage = this * 10
    return "%.2f%%".format(percentage)
}

fun Int.toKFormatString(): String {
    return when {
        this in 1000..999999 -> "%.1fk".format(this / 1000.0)
        this >= 1000000 -> "%.1fm".format(this / 1000000.0)
        else -> this.toString()
    }
}
