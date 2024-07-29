package com.mikhailovskii.aura.test.task.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mikhailovskii.aura.test.task.domain.DataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ScheduleDeletedNotificationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params), KoinComponent {

    private val repository: DataRepository by inject()

    override suspend fun doWork(): Result {
        val config = repository.getDismissedNotificationConfig()
        val attempt = repository.getCurrentAttempt()

        val delay = (((attempt + 1) % config.attempts) * config.duration).toLong()
        WorkManager.getInstance(applicationContext)
            .enqueue(ShowNotificationWorker.buildShowNotificationRequest(delay))
        repository.saveCurrentAttempt(attempt + 1)
        return Result.success()
    }
}