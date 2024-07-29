package com.mikhailovskii.aura.test.task.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mikhailovskii.aura.test.task.domain.DataRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date

class SaveBootEventWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val repository: DataRepository by inject()

    override suspend fun doWork(): Result {
        repository.insertBoot(Date().time)
        return Result.success()
    }
}