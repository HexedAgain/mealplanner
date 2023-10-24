package com.example.lab.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.lab.viewmodel.AddRecipeHandler
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.event.AddRecipeViewEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecipeScreen(addRecipeScreenViewModel: AddRecipeScreenViewModel = koinViewModel()) {
    LazyColumn() {
        item { RecipeTitle(addRecipeHandler = addRecipeScreenViewModel) }
        item {  }
    }
}

@Composable
fun RecipeTitle(addRecipeHandler: AddRecipeHandler) {
    val state = addRecipeHandler.viewState.recipeTitle.collectAsState().value
    OutlinedTextField(
        label = {
            Text("Recipe Title")
        },
        value = state.title,
        onValueChange = {
            addRecipeHandler.postEvent(AddRecipeViewEvent.UpdateRecipeTitle(it))
        }
    )
}