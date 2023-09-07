package com.bagusmerta.utility.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun formatMediaDateYear(date: String?): String? {
    if(!date.isNullOrEmpty()) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.parse(date)
            ?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
    } else {
        return "Unknown"
    }
}

fun formatMediaDateMonth(date: String?): String? {
    if(!date.isNullOrEmpty()) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.parse(date)
            ?.let { SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(it) }
    } else {
        return "Unknown"
    }
}
