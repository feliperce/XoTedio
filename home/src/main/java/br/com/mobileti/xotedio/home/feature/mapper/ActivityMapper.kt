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
        type = type ?: ""
    )

@JvmName("toActivitySuggests")
fun List<ActivityResponse>.toActivitySuggestList() =
    map {
        it.toActivitySuggest()
    }

fun ActivitySuggest.toActivityEntity() =
    ActivityEntity(
        activity = activity,
        accessibility = accessibility,
        key = key,
        link = link,
        participants = participants,
        price = price,
        type = type,
        updatedAt = Date()
    )

fun ActivityEntity.toActivitySuggest() =
    ActivitySuggest(
        accessibility = accessibility,
        activity = activity,
        key = key,
        link = link,
        participants = participants,
        price = price,
        type = type
    )

fun List<ActivityEntity>.toActivitySuggestList() =
    map {
        it.toActivitySuggest()
    }