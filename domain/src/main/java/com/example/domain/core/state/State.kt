package com.example.domain.core.state

import com.example.core.error.ErrorCode

sealed class State<T> {
    data class Error<T>(val errorCode: ErrorCode): State<T>()
    data class Success<T>(val data: T): State<T>()
}