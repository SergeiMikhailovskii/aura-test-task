package com.mikhailovskii.aura.test.task

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mikhailovskii.aura.test.task.data.DefaultDBRepository
import com.mikhailovskii.aura.test.task.domain.DBRepository
import com.mikhailovskii.aura.test.task.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun notificationModule() = module {
    single<AppNotificationManager> { DefaultAppNotificationManager(androidContext()) }
}

fun repositoryModule() = module {
    single<DBRepository> {
        val driver = AndroidSqliteDriver(AuraTestTaskDB.Schema, androidContext())
        DefaultDBRepository(AuraTestTaskDB(driver), Dispatchers.IO)
    }
}

fun viewModelModule() = module {
    viewModel { MainViewModel(get()) }
}