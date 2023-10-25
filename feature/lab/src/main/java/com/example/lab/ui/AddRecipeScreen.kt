package com.example.lab.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.core.ui.UIEventHandler
import com.example.core.ui.UIEventStateHandler
import com.example.lab.viewmodel.AddRecipeHandler
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.AddRecipeState
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecipeScreen(addRecipeScreenViewModel: AddRecipeScreenViewModel = koinViewModel()) {
    LazyColumn() {
        item { RecipeTitle(uiEventHandler = addRecipeScreenViewModel) }
        item {  }
    }
}

@Composable
fun RecipeTitle(uiEventHandler: UIEventStateHandler<AddRecipeState>) {
    val state = uiEventHandler.state.collectAsState().value
    OutlinedTextField(
        label = {
            Text("Recipe Title")
        },
        value = state.recipeTitle,
        onValueChange = {
            uiEventHandler.postEvent(AddRecipeUIEvent.UpdateRecipeTitle(it))
        }
    )
}