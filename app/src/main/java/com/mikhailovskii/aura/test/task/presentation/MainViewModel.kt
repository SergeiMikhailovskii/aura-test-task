package com.mikhailovskii.aura.test.task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhailovskii.aura.test.task.BootData
import com.mikhailovskii.aura.test.task.domain.BootSavedDataType
import com.mikhailovskii.aura.test.task.domain.DBRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

internal class MainViewModel(
    private val repository: DBRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val _uiState = MutableStateFlow<MainState>(MainState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _notificationsFlow = MutableSharedFlow<BootSavedDataType>()
    val notificationsFlow = _notificationsFlow.asSharedFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            repository.getAllBoots().collectLatest {
                resolveScreenState(it)
                resolveNotificationsState(it)
            }
        }
    }

    private fun resolveScreenState(boots: List<BootData>) {
        if (boots.isNotEmpty()) {
            _uiState.value = MainState.WithBootInfo()
        } else {
            _uiState.value = MainState.Empty
        }
    }

    private suspend fun resolveNotificationsState(boots: List<BootData>) {
        if (boots.isEmpty()) {
            _notificationsFlow.emit(BootSavedDataType.None)
        } else if (boots.size == 1) {
            val boot = boots.first()
            val formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
                .format(Date(boot.bootTime))
            _notificationsFlow.emit(BootSavedDataType.Single(formattedDate))
        } else {
            val preLastBoot = boots[boots.size - 2]
            val lastBoot = boots[boots.size - 1]
            val delta = lastBoot.bootTime - preLastBoot.bootTime
            _notificationsFlow.emit(BootSavedDataType.Multiple("$delta"))
        }
    }
}