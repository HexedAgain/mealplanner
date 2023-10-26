package com.example.lab.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.core.navigation.NavScreen
import com.example.lab.ui.AddRecipeScreen
import com.example.lab.ui.theme.AddRecipeScreenScheme
import com.example.lab.ui.theme.LocalAddRecipeScreenTheme
import javax.inject.Inject

class AddRecipeNavScreen(): NavScreen {
    override val routeName: String = "AddRecipe"
    override val showNavBar = false

    @Composable
    override fun Content() {
        CompositionLocalProvider(
            LocalAddRecipeScreenTheme provides AddRecipeScreenScheme()
        ) {
            AddRecipeScreen()
        }
    }
}