package com.example.core.error

interface ErrorCode

enum class DatabaseErrorCode: ErrorCode {
    INSERT_ERROR,
    UPDATE_ERROR,
    DELETE_ERROR,
    FETCH_ERROR
}

enum class UIRecipeErrorCode: ErrorCode {
    INSERT_STEP_ERROR,
    EDIT_STEP_ERROR
}