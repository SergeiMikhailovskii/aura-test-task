package com.mikhailovskii.aura.test.task.presentation

sealed interface MainState {
    data object Empty : MainState
    class WithBootInfo : MainState
}