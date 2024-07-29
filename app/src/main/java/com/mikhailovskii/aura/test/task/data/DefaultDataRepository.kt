package com.mikhailovskii.aura.test.task.data

import android.content.SharedPreferences
import androidx.core.content.edit
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mikhailovskii.aura.test.task.AuraTestTaskDB
import com.mikhailovskii.aura.test.task.domain.DataRepository
import com.mikhailovskii.aura.test.task.domain.DismissedNotificationConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultDataRepository(
    private val database: AuraTestTaskDB,
    private val preferences: SharedPreferences,
    private val ioDispatcher: CoroutineDispatcher
) : DataRepository {

    override fun getAllBoots() =
        database.bootDataQueries.selectAll().asFlow().mapToList(ioDispatcher)

    override fun insertBoot(time: Long) {
        database.bootDataQueries.insertBootData(time)
    }

    override suspend fun saveCurrentAttempt(attempt: Int) = withContext(ioDispatcher) {
        preferences.edit { putInt(CURRENT_ATTEMPT_KEY, attempt) }
    }

    override suspend fun getCurrentAttempt(): Int = withContext(ioDispatcher) {
        preferences.getInt(CURRENT_ATTEMPT_KEY, 0)
    }

    override suspend fun saveDismissedNotificationConfig(config: DismissedNotificationConfig) =
        withContext(ioDispatcher) {
            preferences.edit {
                putInt(ATTEMPT_KEY, config.attempts)
                putInt(DURATION_KEY, config.duration)
            }
        }

    override suspend fun getDismissedNotificationConfig(): DismissedNotificationConfig =
        withContext(ioDispatcher) {
            val attempt = preferences.getInt(ATTEMPT_KEY, 5)
            val duration = preferences.getInt(DURATION_KEY, 15)
            DismissedNotificationConfig(duration = duration, attempts = attempt)
        }

    private companion object {
        const val CURRENT_ATTEMPT_KEY = "CURRENT_ATTEMPT_KEY"
        const val ATTEMPT_KEY = "ATTEMPT_KEY"
        const val DURATION_KEY = "DURATION_KEY"
    }
}