package com.mikhailovskii.aura.test.task.domain

sealed interface BootSavedDataType {
    object None : BootSavedDataType
    class Single(val time: String) : BootSavedDataType
    class Multiple(val delta: String) : BootSavedDataType
}