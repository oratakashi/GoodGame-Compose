package com.oratakashi.goodgame.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.oratakashi.goodgame.BuildConfig
import com.oratakashi.goodgame.Config
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import com.oratakashi.goodgame.data.reqres.web.RawgApiClient
import com.oratakashi.viewbinding.core.tools.retrofit.createOkHttpClient
import com.oratakashi.viewbinding.core.tools.retrofit.createService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun providesChuckerCollector(
        @ApplicationContext context: Context
    ): ChuckerCollector {
        return ChuckerCollector(
            context = context,
            showNotification = BuildConfig.DEBUG,
            retentionPeriod = RetentionManager.Period.ONE_DAY
        )
    }

    @Provides
    @Singleton
    fun providesChuckerInterceptor(
        @ApplicationContext context: Context,
        collector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .apply {
                collector(collector)
                maxContentLength(250_000L)
                alwaysReadResponseBody(false)
            }
            .build()
    }

    @Provides
    @Singleton
    fun providesCustomInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request: Request = chain.request()

            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("key", Config.KEY)
                .build()

            if(!request.url.queryParameterNames.contains("platforms")) {
                url.newBuilder()
                    .addQueryParameter("platforms", "4")
                    .build()
            }
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providesOkHttp(
        interceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        return createOkHttpClient(
            arrayOf(
                interceptor,
                chuckerInterceptor
            ),
            null,
            null,
            BuildConfig.DEBUG
        )
    }

    @Provides
    @Singleton
    fun providesServices(
        okHttpClient: OkHttpClient
    ): RawgApiClient {
        return createService(
            RawgApiClient::class.java,
            okHttpClient,
            Config.BASE_URL
        )
    }

    @Provides
    @Singleton
    fun providesApiClient(
        rawgApiClient: RawgApiClient
    ): RawgApi {
        return RawgApi(rawgApiClient)
    }
}