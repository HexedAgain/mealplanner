package com.example.settings.nav

import androidx.compose.runtime.Composable
import com.example.core.navigation.BottomNavigationItem
import com.example.assets.R
import com.example.settings.ui.SettingsScreen

class SettingsNavScreen: BottomNavigationItem {
    override val navigationIconRes: Int = R.drawable.settings_24
    override val navigationIconText: Int = R.string.bottom_nav_settings
    override val routeName: String = "Settings"

    @Composable
    override fun Content() {
        SettingsScreen()
    }
}