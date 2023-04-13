package br.com.mobileti.xotedio.data.local.converter

import androidx.room.TypeConverter
import br.com.mobileti.xotedio.data.remote.ActivityStatus
import java.util.*

class ActivityStatusConverter {
    @TypeConverter
    fun fromActivityStatus(activityStatus: ActivityStatus?): String? {
        return activityStatus?.status
    }
}