package com.example.stocks

import android.app.Application
import com.example.stocks.di.appModule
import com.example.stocks.utils.di.prefsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)

            androidContext(this@NewsApp)

            modules(
                listOf(
                    appModule,
                    prefsModule
                )
            )
        }
    }
}