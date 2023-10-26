package com.example.core.error

interface ErrorCode

enum class DatabaseErrorCode: ErrorCode {
    INSERT_RECIPE_ERROR,
    UPDATE_ERROR,
    DELETE_ERROR,
    FETCH_ERROR
}

enum class UIRecipeErrorCode: ErrorCode {
    INSERT_STEP_OUT_OF_BOUNDS,
    EDIT_STEP_OUT_OF_BOUNDS,
    UPDATE_STEP_NOT_FOUND,
    DELETE_STEP_NOT_FOUND,
    SAVE_RECIPE_DUPLICATE_STEP,
    SAVE_RECIPE_NO_STEPS,
    SAVE_RECIPE_NO_RECIPE,
    SAVE_RECIPE_INCOMPLETE_STEP,
}