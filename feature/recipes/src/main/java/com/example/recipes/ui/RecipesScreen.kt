package com.example.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipes.viewmodel.RecipesViewModel

@Composable
fun RecipesScreen(recipesViewModel: RecipesViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Box(
            modifier = Modifier.height(100.dp).fillMaxWidth().background(Color.Gray)
        ) {
            Text("Recipes")
        }
    }
}