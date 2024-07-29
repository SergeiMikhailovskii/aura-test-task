package com.mikhailovskii.aura.test.task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhailovskii.aura.test.task.domain.DBRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class MainViewModel(
    private val repository: DBRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val _uiState = MutableStateFlow<MainState>(MainState.Empty)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch(exceptionHandler) {
            repository.getAllBoots().collectLatest {
                if (it.isNotEmpty()) {
                    _uiState.value = MainState.WithBootInfo()
                } else {
                    _uiState.value = MainState.Empty
                }
            }
        }
    }

}