package com.example.recipes.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.ThemedAppBarScreen
import com.example.recipes.ui.theme.LocalRecipeScreenTheme
import com.example.recipes.viewmodel.RecipesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel = koinViewModel()) {
    val theme = LocalRecipeScreenTheme.current
    ThemedAppBarScreen(
        titleResId = theme.text.recipeScreenTitle,
    ) {

    }
}