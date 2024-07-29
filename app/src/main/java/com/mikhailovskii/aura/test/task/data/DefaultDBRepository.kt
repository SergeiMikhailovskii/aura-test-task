package com.mikhailovskii.aura.test.task.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.mikhailovskii.aura.test.task.AuraTestTaskDB
import com.mikhailovskii.aura.test.task.domain.DBRepository
import kotlinx.coroutines.CoroutineDispatcher

class DefaultDBRepository(
    private val database: AuraTestTaskDB,
    private val ioDispatcher: CoroutineDispatcher
) : DBRepository {
    override fun getAllBoots() = database.bootDataQueries.selectAll().asFlow().mapToList(ioDispatcher)

}