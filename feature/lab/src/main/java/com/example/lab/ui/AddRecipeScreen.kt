package com.example.lab.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.ui.UIEventStateHandler
import com.example.domain.recipe.model.RecipeStep
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.AddRecipeState
import com.example.lab.viewmodel.AddRecipeStateEventHandler
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddRecipeScreen(addRecipeScreenViewModel: AddRecipeScreenViewModel = koinViewModel()) {
    val state = addRecipeScreenViewModel.state.collectAsState().value
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                RecipeTitle(stateEventHandler = addRecipeScreenViewModel)
            }
            itemsIndexed(state.steps) { index, step ->
                EditableRecipeStep(
                    step = step,
                    idx = index,
                    uiEventHandler = addRecipeScreenViewModel
                )
            }
//            item {
//                EditableRecipeStep(
//                    step = state.currentStep,
//                    uiEventHandler = addRecipeScreenViewModel
//                )
//            }
        }
        FloatingActionButton(
            onClick = { addRecipeScreenViewModel.postEvent(AddRecipeUIEvent.InsertStep(insertAt = null)) },
            shape = CircleShape,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text  = "Add step",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun RecipeStep(
    step: RecipeStep
) {

}

@Composable
fun RecipeTitle(
    stateEventHandler: AddRecipeStateEventHandler
) {
    val title = stateEventHandler.state.collectAsState().value.recipeTitle
    OutlinedTextField(
        label = {
            Text("Recipe Title")
        },
        value = title,
        onValueChange = {
            stateEventHandler.postEvent(AddRecipeUIEvent.UpdateRecipeTitle(it))
        }
    )
}

@Composable
fun EditableRecipeStep(
    step: RecipeStep,
    idx: Int,
    uiEventHandler: UIEventStateHandler<AddRecipeState>
) {
    Text("Step ${idx}")
    Column {
        OutlinedTextField(
            label = {
                Text("Step Title")
            },
            value = step.title,
            onValueChange = {
                val event = AddRecipeUIEvent.UpdateStep(updateAt = idx, title = it)
                uiEventHandler.postEvent(event)
            }
        )
        OutlinedTextField(
            label = {
                Text("Step Body")
            },
            value = step.body,
            onValueChange = {
                val event = AddRecipeUIEvent.UpdateStep(updateAt = idx, body = it)
                uiEventHandler.postEvent(event)
            }
        )
    }
}