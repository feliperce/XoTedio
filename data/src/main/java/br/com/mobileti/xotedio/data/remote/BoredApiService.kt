package br.com.mobileti.xotedio.data.remote

import br.com.mobileti.xotedio.data.remote.response.ActivityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BoredApiService {

    @GET("/api/activity")
    fun getRandomActivity(
        @Query("type") type: String
    ): Response<ActivityResponse>
}