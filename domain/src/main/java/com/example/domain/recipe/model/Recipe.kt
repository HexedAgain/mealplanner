package com.example.domain.recipe.model

data class Recipe(
    val title: String,
    val steps: List<RecipeStep>
)