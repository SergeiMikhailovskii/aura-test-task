package com.mikhailovskii.aura.test.task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhailovskii.aura.test.task.BootData
import com.mikhailovskii.aura.test.task.domain.DataRepository
import com.mikhailovskii.aura.test.task.domain.DismissedNotificationConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

internal class MainViewModel(private val repository: DataRepository) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val _uiState = MutableStateFlow<MainState>(MainState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            repository.getAllBoots().collectLatest {
                resolveScreenState(it)
            }
        }
    }

    private fun resolveScreenState(boots: List<BootData>) {
        if (boots.isNotEmpty()) {
            val dateTime = DateFormat.getDateInstance(DateFormat.SHORT)
            val mappedBoots = boots.map { dateTime.format(Date(it.bootTime)) }.groupBy { it }
                .map { BootUIData(it.key, it.value.size) }
                .joinToString(separator = "\n") { "${it.date}-${it.amount}" }
            _uiState.value = MainState.WithBootInfo(mappedBoots)
        } else {
            _uiState.value = MainState.Empty
        }
    }

    fun saveDismissedNotificationConfig(config: DismissedNotificationConfig) {
        viewModelScope.launch(exceptionHandler) { repository.saveDismissedNotificationConfig(config) }
    }
}