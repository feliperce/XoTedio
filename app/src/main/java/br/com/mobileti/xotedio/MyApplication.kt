package br.com.mobileti.xotedio

import android.app.Application
import br.com.mobileti.xotedio.data.di.dataModule
import br.com.mobileti.xotedio.home.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                arrayListOf(
                    dataModule,
                    homeModule
                )
            )
        }
    }
}