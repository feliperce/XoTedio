package br.com.mobileti.xotedio.home.feature.home.di

import br.com.mobileti.xotedio.data.remote.BoredApiService
import br.com.mobileti.xotedio.home.feature.home.repository.HomeRepository
import br.com.mobileti.xotedio.home.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val homeModule = module {
    single {
        get<Retrofit>(
            named("retrofit")
        ).create(BoredApiService::class.java)
    }



    factory { HomeRepository(get(), get()) }

    viewModel { HomeViewModel(get()) }
}