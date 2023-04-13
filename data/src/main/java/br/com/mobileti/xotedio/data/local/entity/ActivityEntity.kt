package br.com.mobileti.xotedio.data.local.entity

import androidx.room.*
import br.com.mobileti.xotedio.data.remote.ActivityStatus
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
    val status: ActivityStatus,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "time_spent") val timeSpent: Date?
)