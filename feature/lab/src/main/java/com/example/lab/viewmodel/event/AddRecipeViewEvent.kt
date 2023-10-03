package com.example.lab.viewmodel.event

import com.example.core.ui.ViewEvent

sealed class AddRecipeViewEvent: ViewEvent {
    class UpdateRecipeTitle(val newTitle: String): AddRecipeViewEvent()
}