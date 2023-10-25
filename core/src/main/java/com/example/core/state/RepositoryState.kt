package com.example.core.state

import com.example.core.error.ErrorCode

sealed class RepositoryState<T> {
    data class Error(val errorCode: ErrorCode): RepositoryState<Unit>()
    data class Success<T>(val data: T): RepositoryState<T>()

    fun toUIState(): State<T> {
        return when (this) {
            is Success -> State.Success(data = this.data)
            else -> State.Error(errorCode = (this as Error).errorCode)
        }
    }
}