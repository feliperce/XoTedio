package br.com.mobileti.commons.extensions

import java.text.SimpleDateFormat
import java.util.*


fun Date.getPassedHours(): Long {
    val currentTime = Date().time
    val seconds = this.time - currentTime / 1000
    val minutes = seconds / 60
    val hours = minutes / 60

    return hours
}

fun Long.timeMillisToDateFormatString(pattern: String = "hh"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(Date(this))
}