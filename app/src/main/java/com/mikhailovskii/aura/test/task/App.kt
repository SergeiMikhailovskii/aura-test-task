package com.mikhailovskii.aura.test.task

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    private val appNotificationManager: AppNotificationManager by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(viewModelModule(), repositoryModule(), notificationModule())
        }

        appNotificationManager.createChannels()
    }
}