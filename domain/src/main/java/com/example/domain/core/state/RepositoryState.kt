package com.example.domain.core.state

import com.example.core.error.ErrorCode

sealed class RepositoryState<T> {
    data class Error<T>(val errorCode: ErrorCode): RepositoryState<T>()
    data class Success<T>(val errorCode: ErrorCode): RepositoryState<T>()
}