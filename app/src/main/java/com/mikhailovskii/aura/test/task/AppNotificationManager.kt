package com.mikhailovskii.aura.test.task

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.mikhailovskii.aura.test.task.domain.BootSavedDataType

interface AppNotificationManager {
    fun createChannels()
    fun showBootNotification(dataType: BootSavedDataType)
}

internal class DefaultAppNotificationManager(
    private val context: Context
) : AppNotificationManager {

    private val notificationManager by lazy { requireNotNull(context.getSystemService<NotificationManager>()) }

    override fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, "Channel name", importance)

            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun showBootNotification(dataType: BootSavedDataType) {
        val contentText = when (dataType) {
            BootSavedDataType.None -> "No boots detected"
            is BootSavedDataType.Single -> "The boot was detected = ${dataType.time}"
            is BootSavedDataType.Multiple -> "Last boots time delta = ${dataType.delta}"
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Boot notification")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(12345, notification)
    }

    private companion object {
        const val CHANNEL_ID = "1234"
    }
}