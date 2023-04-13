package br.com.mobileti.commons.extensions

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue


fun Date.getPassedHours(secondDate: Date = Date()): Long {
    val firstTime = this.time
    val secondTime = secondDate.time

    return if (secondTime > firstTime) {
        val diffInMillisec: Long = (this.time - secondDate.time).absoluteValue

        TimeUnit.MILLISECONDS.toHours(diffInMillisec)
    } else {
        0L
    }
}

fun Long.timeMillisToDateFormatString(pattern: String = "hh"): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(Date(this))
}