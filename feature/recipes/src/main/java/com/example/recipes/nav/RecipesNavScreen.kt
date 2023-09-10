package com.example.recipes.nav

import androidx.compose.runtime.Composable
import com.example.core.navigation.BottomNavigationItem
import com.example.assets.R
import com.example.recipes.ui.RecipesScreen
import javax.inject.Inject

class RecipesNavScreen @Inject constructor(): BottomNavigationItem {
    override val navigationIconRes: Int = R.drawable.library_24
    override val navigationIconText: Int = R.string.bottom_nav_recipes

    override val routeName: String = "Recipes"
    override val navOrder = 1

    @Composable
    override fun Content() {
        RecipesScreen()
    }
}