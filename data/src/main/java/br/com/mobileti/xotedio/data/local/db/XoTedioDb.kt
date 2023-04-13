package br.com.mobileti.xotedio.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.mobileti.xotedio.data.local.converter.DateConverter
import br.com.mobileti.xotedio.data.local.dao.ActivityDao
import br.com.mobileti.xotedio.data.local.entity.ActivityEntity

@Database(entities = [
    ActivityEntity::class
],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class XoTedioDb : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}