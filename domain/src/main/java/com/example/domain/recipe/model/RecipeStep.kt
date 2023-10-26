package com.example.domain.recipe.model

import java.util.UUID

data class RecipeStep(
    val title: String,
    val body: String,
    val id: String = UUID.randomUUID().toString(), // this one is going to be the lazy list key
) {
    companion object {
        fun empty(): RecipeStep {
            return RecipeStep("", "")
        }
    }
}