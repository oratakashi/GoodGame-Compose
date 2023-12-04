package com.oratakashi.goodgame

import android.app.Application
import com.oratakashi.goodgame.di.apiModules
import com.oratakashi.goodgame.di.reqresModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    apiModules,
                    reqresModule
                )
            )
        }
    }
}