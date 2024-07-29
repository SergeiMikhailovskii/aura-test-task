package com.mikhailovskii.aura.test.task

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DeleteNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        println("delete receiver")
    }
}