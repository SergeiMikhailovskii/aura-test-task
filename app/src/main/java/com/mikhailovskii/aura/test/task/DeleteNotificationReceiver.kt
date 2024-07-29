package com.mikhailovskii.aura.test.task

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mikhailovskii.aura.test.task.data.worker.ScheduleDeletedNotificationWorker

class DeleteNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        WorkManager.getInstance(context)
            .enqueue(OneTimeWorkRequestBuilder<ScheduleDeletedNotificationWorker>().build())
    }
}