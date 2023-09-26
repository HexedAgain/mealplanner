package com.example.core.ui.theme.subtheme

import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

interface AppBarText {
    val titleTextStyle: TextStyle
}

interface AppBarIcon {
    val backArrow: Int
}

interface AppBarColour {
    @get:Composable val backgroundColour: Color
    @get:Composable val foregroundColour: Color
    @get:Composable val appBarColours: TopAppBarColors
}

interface AppBarModifier {

}

interface AppBarContentDescription {
    val backArrow: Int
}

interface AppBarTheme {
    val text: AppBarText
    val icon: AppBarIcon
    val colour: AppBarColour
    val modifier: AppBarModifier
    val contentDesc: AppBarContentDescription
}