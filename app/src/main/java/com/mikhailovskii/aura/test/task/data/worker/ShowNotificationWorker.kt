package com.mikhailovskii.aura.test.task.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mikhailovskii.aura.test.task.AppNotificationManager
import com.mikhailovskii.aura.test.task.domain.BootSavedDataType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

class ShowNotificationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {

    private val appNotificationManager: AppNotificationManager by inject()

    override suspend fun doWork(): Result {
        appNotificationManager.showBootNotification(BootSavedDataType.None)
        WorkManager.getInstance(applicationContext).enqueue(buildShowNotificationRequest())
        return Result.success()
    }

    companion object {
        const val TAG = "ShowNotificationWorker"

        fun buildShowNotificationRequest() =
            OneTimeWorkRequestBuilder<ShowNotificationWorker>().setInitialDelay(
                15,
                TimeUnit.MINUTES
            ).build()
    }
}