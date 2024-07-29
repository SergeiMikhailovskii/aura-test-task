package com.mikhailovskii.aura.test.task

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mikhailovskii.aura.test.task.data.DefaultDataRepository
import com.mikhailovskii.aura.test.task.domain.DataRepository
import com.mikhailovskii.aura.test.task.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun notificationModule() = module {
    single<AppNotificationManager> { DefaultAppNotificationManager(androidContext()) }
}

fun repositoryModule() = module {
    single<DataRepository> {
        val preferences =
            androidContext().getSharedPreferences("aura.test.task", Context.MODE_PRIVATE)
        val driver = AndroidSqliteDriver(AuraTestTaskDB.Schema, androidContext())
        DefaultDataRepository(AuraTestTaskDB(driver), preferences, Dispatchers.IO)
    }
}

fun viewModelModule() = module {
    viewModel { MainViewModel(get()) }
}