package com.example.core.ui.theme.subtheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.assets.R
import com.example.assets.theme.darkBlue
import com.example.assets.theme.typography

val appBarText = object: AppBarText {
    override val titleTextStyle: TextStyle = typography.titleMedium
}

val appBarColour = object: AppBarColour {
    override val appBarColours: TopAppBarColors
        @Composable get() = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = darkBlue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    override val backgroundColour: Color
        @Composable get() = MaterialTheme.colorScheme.primary
    override val foregroundColour: Color
        @Composable get() = MaterialTheme.colorScheme.onPrimary

}

val appBarIcon = object: AppBarIcon {
    override val backArrow = R.drawable.arrow_back_24
}

val appBarModifier = object: AppBarModifier {

}

val appBarContentDescription = object: AppBarContentDescription {
    override val backArrow: Int = R.string.content_desc_back_arrow
}

class AppBarSchemeLight: AppBarTheme {
    override val text: AppBarText = appBarText
    override val icon: AppBarIcon = appBarIcon
    override val colour: AppBarColour = appBarColour
    override val modifier: AppBarModifier = appBarModifier
    override val contentDesc: AppBarContentDescription = appBarContentDescription
}