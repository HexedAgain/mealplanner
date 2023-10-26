package com.example.core.ui.theme.subtheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.assets.R
import com.example.assets.theme.*

val appBarText = object: AppBarText {
    override val titleTextStyle: TextStyle = typography.titleMedium
}

val appBarColourLight = object: AppBarColour {
    override val appBarColours: TopAppBarColors
        @Composable get() = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = darkBlue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )

    override val actionBarActionTint = Color.White
}

val appBarColourDark = object: AppBarColour {
    override val appBarColours: TopAppBarColors
        @Composable get() = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = darkGrey,
            titleContentColor = lightGrey,
            navigationIconContentColor = lightGrey
        )
    override val actionBarActionTint = Color.White
}

val appBarIcon = object: AppBarIcon {
    override val backArrow = R.drawable.arrow_back_24
}

val appBarModifier = object: AppBarModifier {
    override val appBarRoot = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
}

val appBarContentDescription = object: AppBarContentDescription {
    override val backArrow: Int = R.string.content_desc_back_arrow
}

class AppBarScheme: AppBarTheme {
    override val text: AppBarText = appBarText
    override val icon: AppBarIcon = appBarIcon
    override val colour: AppBarColour
    @Composable get() = if (isSystemInDarkTheme()) appBarColourDark else appBarColourLight
    override val modifier: AppBarModifier = appBarModifier
    override val contentDesc: AppBarContentDescription = appBarContentDescription
}