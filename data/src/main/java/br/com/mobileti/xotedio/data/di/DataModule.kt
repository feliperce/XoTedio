package br.com.mobileti.xotedio.data.di

import androidx.room.Room
import br.com.mobileti.xotedio.data.BuildConfig
import br.com.mobileti.xotedio.data.local.db.XoTedioDb
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            XoTedioDb::class.java, "XoTedio"
        ).build()
    }

    fun retrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else HttpLoggingInterceptor.Level.NONE

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        val client = clientBuilder.build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("retrofit")) { retrofit() }

    single { get<XoTedioDb>().activityDao() }
}