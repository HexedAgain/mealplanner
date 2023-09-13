package com.example.settings.nav

import androidx.compose.runtime.Composable
import com.example.core.navigation.BottomNavigationItem
import com.example.assets.R
import com.example.settings.ui.SettingsScreen
import javax.inject.Inject

class SettingsNavScreen @Inject constructor(): BottomNavigationItem {
    override val navigationIconRes: Int = R.drawable.settings_24
    override val navigationIconText: Int = R.string.bottom_nav_settings
    override val routeName: String = "Settings"
    override val navOrder = 3
    override val isDialog = false

    @Composable
    override fun Content() {
        SettingsScreen()
    }
}