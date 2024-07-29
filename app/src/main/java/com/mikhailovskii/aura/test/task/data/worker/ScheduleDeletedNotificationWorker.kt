package com.mikhailovskii.aura.test.task.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ScheduleDeletedNotificationWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {



    override suspend fun doWork(): Result {

        return Result.success()
    }
}