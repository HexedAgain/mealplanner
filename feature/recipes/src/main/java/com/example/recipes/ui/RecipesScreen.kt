package com.example.recipes.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.viewmodel.RecipesViewModel

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel = hiltViewModel()) {
    Text("Recipes")
}