package com.example.core.state

import com.example.core.error.ErrorCode

sealed class State<T> {
    data class Error<T>(val errorCode: ErrorCode): State<T>()
    class Loading<T>: State<T>()
    class Empty<T>: State<T>()
    data class Success<T>(val data: T): State<T>()

    fun State.Error<T>.toUnitError(): State.Error<Unit> {
        return State.Error(errorCode = this.errorCode)
    }
}