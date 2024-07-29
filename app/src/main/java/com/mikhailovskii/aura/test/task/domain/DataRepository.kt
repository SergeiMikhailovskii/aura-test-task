package com.mikhailovskii.aura.test.task.domain

import com.mikhailovskii.aura.test.task.BootData
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getAllBoots(): Flow<List<BootData>>
    fun insertBoot(time: Long)
    suspend fun saveCurrentAttempt(attempt: Int)
    suspend fun getCurrentAttempt(): Int
    suspend fun saveDismissedNotificationConfig(config: DismissedNotificationConfig)
    suspend fun getDismissedNotificationConfig(): DismissedNotificationConfig
}