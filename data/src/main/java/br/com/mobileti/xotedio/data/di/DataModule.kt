package br.com.mobileti.xotedio.data.di

import androidx.room.Room
import br.com.mobileti.xotedio.data.local.db.XoTedioDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            XoTedioDb::class.java, "XoTedio"
        ).build()
    }

    single { get<XoTedioDb>().activityDao() }
}