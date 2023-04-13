package br.com.mobileti.commons.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue


fun Date.getPassedHours(secondDate: Date = Date()): Long {
    val diffInMillisec: Long = (this.getTime() - secondDate.getTime()).absoluteValue

    val diffInHours: Long = TimeUnit.MILLISECONDS.toHours(diffInMillisec)

    return diffInHours
}

fun Long.timeMillisToDateFormatString(pattern: String = "hh"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(Date(this))
}