package com.example.lab.viewmodel.event

import com.example.core.ui.UIEvent

sealed class AddRecipeUIEvent: UIEvent {
    class UpdateRecipeTitle(val newTitle: String): AddRecipeUIEvent()
    class MarkStepForEdit(val stepIndex: Int): AddRecipeUIEvent()
    class DeleteStep(val deleteAt: Int): AddRecipeUIEvent()
    class InsertStep(val insertAt: Int?): AddRecipeUIEvent()
    object UpdateStep: AddRecipeUIEvent()
    object SaveRecipe: AddRecipeUIEvent()
}