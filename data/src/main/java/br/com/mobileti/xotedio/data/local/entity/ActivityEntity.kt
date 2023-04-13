package br.com.mobileti.xotedio.data.local.entity

import androidx.room.*
import java.util.*

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val activityId: Long = 0,
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Date
)