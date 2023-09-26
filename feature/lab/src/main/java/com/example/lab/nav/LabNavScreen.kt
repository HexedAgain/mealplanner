package com.example.lab.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.core.navigation.BottomNavigationItem
import com.example.assets.R
import com.example.lab.ui.LabScreen
import com.example.lab.ui.theme.LabScreenSchemeLight
import com.example.lab.ui.theme.LocalLabScreenTheme
import javax.inject.Inject

class LabNavScreen @Inject constructor(): BottomNavigationItem {
    override val navigationIconRes = R.drawable.science_24
    override val navigationIconText = R.string.bottom_nav_lab
    override val navOrder = 2
    override val isDialog = false
    override val routeName = "Lab"

    @Composable
    override fun Content() {
        CompositionLocalProvider(
            LocalLabScreenTheme provides LabScreenSchemeLight()
        ) {
            LabScreen()
        }
    }
}