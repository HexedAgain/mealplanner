package com.example.core.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface UIEvent
interface UIState
interface UIResult

interface UIEventStateHandler<S: UIState> {
    fun postEvent(event: UIEvent)
    val state: StateFlow<S>
}

/*
    view -> event -> VM - .... sync or async processing ... -> result -> reducer -> state -> view
                         \
                          -> reducer -> state -> view
 */
abstract class Reducer<S: UIState, E: UIEvent, R: UIResult>(initialState: S) {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    fun setState(newState: S) {
        _state.value = newState
    }

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun sendResult(event: R) {
        reduce(_state.value, event)
    }

    abstract fun reduce(oldState: S, result: R)

    abstract fun reduce(oldState: S, event: E)
}