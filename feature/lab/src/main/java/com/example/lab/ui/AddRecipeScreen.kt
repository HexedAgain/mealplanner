package com.example.lab.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.core.ui.UIEventStateHandler
import com.example.domain.recipe.model.RecipeStep
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.AddRecipeState
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecipeScreen(addRecipeScreenViewModel: AddRecipeScreenViewModel = koinViewModel()) {
    val state = addRecipeScreenViewModel.state.collectAsState().value
    LazyColumn() {
        item { RecipeTitle(uiEventHandler = addRecipeScreenViewModel, recipeTitle = state.recipeTitle) }
        items(state.steps) {
            RecipeStep(step = it)
        }
        item {
            EditableRecipeStep(
                step = state.currentStep,
                uiEventHandler = addRecipeScreenViewModel
            )
        }
        item {
            Button(onClick = {}) {
                Text("Add step")
            }
        }
    }
}

@Composable
fun RecipeStep(
    step: RecipeStep
) {

}

@Composable
fun RecipeTitle(uiEventHandler: UIEventStateHandler<AddRecipeState>) {
}

@Composable
fun RecipeTitle(
    recipeTitle: String,
    uiEventHandler: UIEventStateHandler<AddRecipeState>
) {
    OutlinedTextField(
        label = {
            Text("Recipe Title")
        },
        value = recipeTitle,
        onValueChange = {
            uiEventHandler.postEvent(AddRecipeUIEvent.UpdateRecipeTitle(it))
        }
    )
}

@Composable
fun EditableRecipeStep(
    step: RecipeStep,
    idx: Int? = null,
    uiEventHandler: UIEventStateHandler<AddRecipeState>
) {
    Column {
        OutlinedTextField(
            label = {
                Text("Step Title")
            },
            value = step.title,
            onValueChange = {
            }
        )
        OutlinedTextField(
            label = {
                Text("Step Body")
            },
            value = step.body,
            onValueChange = {
            }
        )
    }
}