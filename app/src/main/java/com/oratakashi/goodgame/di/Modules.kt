package com.oratakashi.goodgame.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.oratakashi.goodgame.BuildConfig
import com.oratakashi.goodgame.Config
import com.oratakashi.goodgame.data.reqres.GamesDataStore
import com.oratakashi.goodgame.data.reqres.GamesRepository
import com.oratakashi.goodgame.data.reqres.GenreDataStore
import com.oratakashi.goodgame.data.reqres.GenreRepository
import com.oratakashi.goodgame.data.reqres.PlatformDataStore
import com.oratakashi.goodgame.data.reqres.PlatformRepository
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import com.oratakashi.goodgame.data.reqres.web.RawgApiClient
import com.oratakashi.goodgame.domain.GamesInteractor
import com.oratakashi.goodgame.domain.GamesUsecase
import com.oratakashi.goodgame.domain.GenreInteractor
import com.oratakashi.goodgame.domain.GenreUsecase
import com.oratakashi.goodgame.domain.PlatformUsecase
import com.oratakashi.goodgame.domain.PlatfromInteractor
import com.oratakashi.goodgame.presentation.menu.genre.GenreViewModel
import com.oratakashi.goodgame.presentation.menu.home.HomeViewModel
import com.oratakashi.viewbinding.core.tools.retrofit.createOkHttpClient
import com.oratakashi.viewbinding.core.tools.retrofit.createService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val apiModules = module {
    single {
        ChuckerCollector(
            context = androidContext(),
            showNotification = BuildConfig.DEBUG,
            retentionPeriod = RetentionManager.Period.ONE_DAY
        )
    }

    single {
        ChuckerInterceptor.Builder(androidContext())
            .apply {
                collector(get())
                maxContentLength(250_000L)
                alwaysReadResponseBody(false)
            }
            .build()
    }

    single {
        Interceptor { chain ->
            var request: Request = chain.request()

            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("key", Config.KEY)
                .build()

            if (!request.url.queryParameterNames.contains("platforms")) {
                url.newBuilder()
                    .addQueryParameter("platforms", "4")
                    .build()
            }
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    single {
        createOkHttpClient(
            arrayOf(
                get(),
                get<ChuckerInterceptor>()
            ),
            null,
            null,
            BuildConfig.DEBUG
        )
    }

    single {
        createService(
            RawgApiClient::class.java,
            get(),
            Config.BASE_URL
        )
    }
}

val reqresModule = module {
    single { RawgApi(get()) }

    singleOf(::GenreDataStore) bind GenreRepository::class
    singleOf(::GenreInteractor) bind GenreUsecase::class

    singleOf(::PlatformDataStore) bind PlatformRepository::class
    singleOf(::PlatfromInteractor) bind PlatformUsecase::class

    singleOf(::GamesDataStore) bind GamesRepository::class
    singleOf(::GamesInteractor) bind GamesUsecase::class

    viewModelOf(::GenreViewModel)
    viewModelOf(::HomeViewModel)
}