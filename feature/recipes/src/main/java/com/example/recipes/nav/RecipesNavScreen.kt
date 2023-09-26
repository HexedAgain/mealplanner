package com.example.recipes.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.core.navigation.BottomNavigationItem
import com.example.assets.R
import com.example.recipes.ui.RecipesScreen
import com.example.recipes.ui.theme.LocalRecipeScreenTheme
import com.example.recipes.ui.theme.RecipeScreenScheme
import javax.inject.Inject

class RecipesNavScreen @Inject constructor(): BottomNavigationItem {
    override val navigationIconRes: Int = R.drawable.library_24
    override val navigationIconText: Int = R.string.bottom_nav_recipes

    override val routeName: String = "Recipes"
    override val navOrder = 1
    override val isDialog = false

    @Composable
    override fun Content() {
        CompositionLocalProvider(
            LocalRecipeScreenTheme provides RecipeScreenScheme()
        ) {
            RecipesScreen()
        }
    }
}