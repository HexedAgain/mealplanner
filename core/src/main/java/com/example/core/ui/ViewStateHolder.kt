package com.example.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ViewStateHolder<T> {
    val viewState: T

    fun <T> updateState (state: StateFlow<T>, action: (MutableStateFlow<T>) -> Unit) {
        action(state as MutableStateFlow<T>)
    }
}