package com.example.recipes.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

val LocalRecipeScreenTheme = staticCompositionLocalOf<RecipeScreenTheme> {
    throw java.lang.IllegalStateException("No RecipeScreenTheme provided")
}

interface RecipeScreenText {
    val recipeScreenTitle: Int
}

interface RecipeScreenTheme {
    val text: RecipeScreenText
}