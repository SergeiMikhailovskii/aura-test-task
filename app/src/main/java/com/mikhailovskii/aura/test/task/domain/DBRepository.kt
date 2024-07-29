package com.mikhailovskii.aura.test.task.domain

import com.mikhailovskii.aura.test.task.BootData
import kotlinx.coroutines.flow.Flow

interface DBRepository {
    fun getAllBoots(): Flow<List<BootData>>
}