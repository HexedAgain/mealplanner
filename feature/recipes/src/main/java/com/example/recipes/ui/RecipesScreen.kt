package com.example.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.ThemedAppBar
import com.example.recipes.ui.theme.LocalRecipeScreenTheme
import com.example.recipes.viewmodel.RecipesViewModel

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel = hiltViewModel()) {
    val theme = LocalRecipeScreenTheme.current
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        ThemedAppBar(
            titleResId = theme.text.recipeScreenTitle,
            navigator = recipesViewModel
        )
    }
}