package com.example.lab.viewmodel.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// TODO could there be a single store of this state?
//      Could we enforce a single viewmodel access to any particular state?
data class AddRecipeViewState(
    val recipeTitle: StateFlow<RecipeTitleViewState> = MutableStateFlow(RecipeTitleViewState())
) {
    data class RecipeTitleViewState(
        val title: String = ""
    )
}