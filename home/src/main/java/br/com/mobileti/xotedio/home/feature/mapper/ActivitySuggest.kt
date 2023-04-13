package br.com.mobileti.xotedio.home.feature.mapper

import br.com.mobileti.xotedio.data.remote.ActivityStatus
import java.util.Date

data class ActivitySuggest(
    val activityId: Long,
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String,
    val status: ActivityStatus = ActivityStatus.RUNNING,
    val timeSpent: Date? = null,
    val createdAt: Date
)
