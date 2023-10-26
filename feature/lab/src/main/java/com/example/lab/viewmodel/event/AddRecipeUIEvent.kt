package com.example.lab.viewmodel.event

import com.example.core.ui.UIEvent

sealed class AddRecipeUIEvent: UIEvent {
    class UpdateRecipeTitle(val newTitle: String): AddRecipeUIEvent()
    class MarkStepForEdit(val stepIndex: Int): AddRecipeUIEvent()
    class DeleteStep(val deleteAt: Int): AddRecipeUIEvent()
    class InsertStep(val insertAt: Int?): AddRecipeUIEvent()
    class UpdateStep(
        val updateAt: Int,
        val title: String? = null,
        val body: String? = null
    ): AddRecipeUIEvent()
    object SaveRecipe: AddRecipeUIEvent()
}