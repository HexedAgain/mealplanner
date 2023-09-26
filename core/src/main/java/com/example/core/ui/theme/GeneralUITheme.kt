package com.example.core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.core.ui.theme.subtheme.AppBarTheme

val LocalGeneralUITheme = staticCompositionLocalOf<GeneralUITheme> {
    throw java.lang.IllegalStateException("No GeneralUITheme provided")
}

interface GeneralUITheme {
    val appBar: AppBarTheme
}