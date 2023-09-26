package com.example.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.example.core.ui.theme.subtheme.AppBarScheme
import com.example.core.ui.theme.subtheme.AppBarTheme

class GeneralUISchemeLight: GeneralUITheme {
    override val appBar: AppBarTheme = AppBarScheme()
}

class GeneralUISchemeDark: GeneralUITheme {
    override val appBar: AppBarTheme = AppBarScheme()
}

class GeneralUIScheme() {
    @Composable
    operator fun invoke() {
        val isDarkMode = isSystemInDarkTheme()
    }
}