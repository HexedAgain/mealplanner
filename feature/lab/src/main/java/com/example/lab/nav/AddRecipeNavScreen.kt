package com.example.lab.nav

import androidx.compose.runtime.Composable
import com.example.core.navigation.NavScreen
import com.example.lab.ui.AddRecipeScreen
import javax.inject.Inject

class AddRecipeNavScreen(): NavScreen {
    override val routeName: String = "AddRecipe"
    override val showNavBar = false

    @Composable
    override fun Content() {
        AddRecipeScreen()
    }
}