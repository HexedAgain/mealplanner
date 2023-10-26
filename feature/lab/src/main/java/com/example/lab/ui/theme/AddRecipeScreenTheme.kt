package com.example.lab.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import java.lang.IllegalStateException

val LocalAddRecipeScreenTheme = staticCompositionLocalOf<AddRecipeScreenTheme> {
    throw IllegalStateException("No AddRecipeScreenTheme provided")
}

interface AddRecipeScreenText {
    val title: Int
}

interface AddRecipeScreenIcon {
    val saveRecipe: Int
}

interface AddRecipeScreenModifier {
}

interface AddRecipeScreenColour {
}

interface AddRecipeScreenTheme {
    val text: AddRecipeScreenText
    val colour: AddRecipeScreenColour
    val modifier: AddRecipeScreenModifier
    val icon: AddRecipeScreenIcon
}