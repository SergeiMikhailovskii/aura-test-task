package com.mikhailovskii.aura.test.task.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mikhailovskii.aura.test.task.AppNotificationManager
import com.mikhailovskii.aura.test.task.domain.BootsToBootSavedDataTypeMapper
import com.mikhailovskii.aura.test.task.domain.DataRepository
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class ShowNotificationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {

    private val appNotificationManager: AppNotificationManager by inject()
    private val repository: DataRepository by inject()

    override suspend fun doWork(): Result {
        val allBoots = repository.getAllBoots().first()
        val dataType = BootsToBootSavedDataTypeMapper().invoke(allBoots)
        appNotificationManager.showBootNotification(dataType)
        WorkManager.getInstance(applicationContext).enqueue(buildShowNotificationRequest())
        return Result.success()
    }

    companion object {
        const val TAG = "ShowNotificationWorker"

        fun buildShowNotificationRequest(delay: Long = 15) =
            OneTimeWorkRequestBuilder<ShowNotificationWorker>().setInitialDelay(
                delay,
                TimeUnit.MINUTES
            ).build()
    }
}