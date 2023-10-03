package com.example.lab.viewmodel.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class AddRecipeViewState(
    val recipeTitle: StateFlow<RecipeTitleViewState> = MutableStateFlow(RecipeTitleViewState())
) {
    data class RecipeTitleViewState(
        val title: String = ""
    )
}