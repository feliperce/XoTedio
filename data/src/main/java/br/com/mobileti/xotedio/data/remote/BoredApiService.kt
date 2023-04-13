package br.com.mobileti.xotedio.data.remote

import br.com.mobileti.xotedio.data.remote.response.ActivityResponse
import retrofit2.http.GET

interface BoredApiService {

    @GET("api/activity/")
    fun getRandomActivity(): ActivityResponse
}