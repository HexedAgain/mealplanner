package com.example.lab.viewmodel.event

import com.example.core.error.ErrorCode
import com.example.core.state.State
import com.example.core.ui.UIResult

sealed class AddRecipeUIResult: UIResult {
    class SaveRecipe: AddRecipeUIResult()
    class Error(val errorCode: ErrorCode): AddRecipeUIResult()
}