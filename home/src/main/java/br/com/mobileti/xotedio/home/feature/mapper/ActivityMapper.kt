package br.com.mobileti.xotedio.home.feature.mapper

import br.com.mobileti.xotedio.data.local.entity.ActivityEntity
import br.com.mobileti.xotedio.data.remote.response.ActivityResponse
import java.util.*

fun ActivityResponse.toActivitySuggest() =
    ActivitySuggest(
        accessibility = accessibility ?: 0.0,
        activity = activity ?: "",
        key = key ?: "",
        link = link ?: "",
        participants = participants ?: 0,
        price = price ?: 0.0,
        type = type ?: "",
        createdAt = Date(),
        activityId = 0
    )

@JvmName("toActivitySuggests")
fun List<ActivityResponse>.toActivitySuggestList() =
    map {
        it.toActivitySuggest()
    }

fun ActivitySuggest.toActivityEntity() =
    ActivityEntity(
        activityId = activityId,
        activity = activity,
        accessibility = accessibility,
        key = key,
        link = link,
        participants = participants,
        price = price,
        type = type,
        createdAt = createdAt,
        status = status,
        timeSpent = timeSpent
    )

fun ActivityEntity.toActivitySuggest() =
    ActivitySuggest(
        accessibility = accessibility,
        activity = activity,
        key = key,
        link = link,
        participants = participants,
        price = price,
        type = type,
        status = status,
        timeSpent = timeSpent,
        createdAt = createdAt,
        activityId = activityId
    )

fun List<ActivityEntity>.toActivitySuggestList() =
    map {
        it.toActivitySuggest()
    }