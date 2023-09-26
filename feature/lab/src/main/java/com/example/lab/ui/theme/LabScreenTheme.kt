package com.example.lab.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

val LocalLabScreenTheme = staticCompositionLocalOf<LabScreenTheme> {
    throw java.lang.IllegalStateException("No LabScreenTheme provided")
}

interface LabScreenText {
    val addNewRecipe: Int
    val labScreenTitle: Int
}

interface LabScreenModifier {
    val labScreenRoot: Modifier
}

interface LabScreenTheme {
    val text: LabScreenText
    val modifier: LabScreenModifier
}