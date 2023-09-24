package com.example.domain.recipe.model

data class RecipeStep(
    val title: String,
    val body: String,
//    val idx: Int
) {
    companion object {
        fun empty(): RecipeStep {
            return RecipeStep("", "")
        }
    }
}