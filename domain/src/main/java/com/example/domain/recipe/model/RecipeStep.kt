package com.example.domain.recipe.model

data class RecipeStep(
    val title: String,
    val body: String,
) {
    companion object {
        fun empty(): RecipeStep {
            return RecipeStep("", "")
        }
    }
}