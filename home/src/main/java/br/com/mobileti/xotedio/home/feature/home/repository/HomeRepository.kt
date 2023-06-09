package br.com.mobileti.xotedio.home.feature.home.repository

import br.com.mobileti.xotedio.data.Resource
import br.com.mobileti.xotedio.data.local.dao.ActivityDao
import br.com.mobileti.xotedio.data.remote.BoredApiService
import br.com.mobileti.xotedio.data.remote.extension.callNetworkData
import br.com.mobileti.xotedio.home.feature.mapper.ActivitySuggest
import br.com.mobileti.xotedio.home.feature.mapper.toActivityEntity
import br.com.mobileti.xotedio.home.feature.mapper.toActivitySuggest
import br.com.mobileti.xotedio.home.feature.mapper.toActivitySuggestList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class HomeRepository(
    private val boredApiService: BoredApiService,
    private val activityDao: ActivityDao
) {

    fun insertRandomActivitySuggest(
        type: String
    ) = flow {

        val data = callNetworkData(
            remote = {
                boredApiService.getRandomActivity(type = type)
            },
            mapper = { it?.toActivitySuggest() }
        )

        emitAll(data)
    }.flowOn(Dispatchers.Default)
    .onEach { res ->
        if (res is Resource.Success) {
            res.data?.let {
                activityDao.insertActivity(it.toActivityEntity())
            }
        }
    }
    .flowOn(Dispatchers.IO)

    suspend fun getAllActivitySuggest() =
        activityDao.getAllActivities().flowOn(Dispatchers.IO).map {
            it.toActivitySuggestList()
        }


    suspend fun updateActivitySuggest(activitySuggest: ActivitySuggest) = withContext(Dispatchers.IO) {
        activityDao.updateActivity(activitySuggest.toActivityEntity())
    }

    suspend fun removeActivitySuggest(activitySuggest: ActivitySuggest) = withContext(Dispatchers.IO) {
        activityDao.deleteActivity(activitySuggest.toActivityEntity())
    }

}