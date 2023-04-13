package br.com.mobileti.xotedio.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.mobileti.xotedio.data.local.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {

    @Insert
    fun insertActivity(activityEntity: ActivityEntity)

    @Insert
    fun insertActivityList(activityEntityList: List<ActivityEntity>)

    @Delete
    fun deleteActivity(activityEntity: ActivityEntity)

    @Query("SELECT * FROM activities")
    fun getAllActivities(): Flow<List<ActivityEntity>>

    @Update
    fun updateActivity(activityEntity: ActivityEntity)

}