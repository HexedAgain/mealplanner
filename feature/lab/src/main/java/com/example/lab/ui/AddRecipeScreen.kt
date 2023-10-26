package com.example.lab.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core.ui.CollapsableThemedAppBarScreen
import com.example.core.ui.ThemedActionButton
import com.example.core.ui.ThemedAppBarScreen
import com.example.core.ui.UIEventStateHandler
import com.example.domain.recipe.model.RecipeStep
import com.example.lab.ui.theme.LocalAddRecipeScreenTheme
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.AddRecipeState
import com.example.lab.viewmodel.AddRecipeStateEventHandler
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.component.getScopeId

@Composable
fun AddRecipeScreen(addRecipeScreenViewModel: AddRecipeScreenViewModel = koinViewModel()) {
    val theme = LocalAddRecipeScreenTheme.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd,
    ) {
        CollapsableThemedAppBarScreen(
            titleResId = theme.text.title,
            navigator = koinInject(),
            rhsActions = { SaveAction(stateEventHandler = addRecipeScreenViewModel) }
        ) {
            RecipeList()
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
fun SaveAction(
    stateEventHandler: AddRecipeStateEventHandler
) {
    val theme = LocalAddRecipeScreenTheme.current
    ThemedActionButton(
        onClick = { stateEventHandler.postEvent(AddRecipeUIEvent.SaveRecipe) },
        iconResId = theme.icon.saveRecipe
    )
//    IconButton(onClick = { /*TODO*/ }) {
//        Icon(
//            painter = painterResource(id = theme.icon.saveRecipe),
//            contentDescription = null
//        )
//    }
}

@Composable
fun RecipeList(addRecipeScreenViewModel: AddRecipeScreenViewModel = koinViewModel()) {
    val state = addRecipeScreenViewModel.state.collectAsState().value
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            RecipeTitle(stateEventHandler = addRecipeScreenViewModel)
        }
        itemsIndexed(state.steps, key = {x, y -> y.id}) { index, step ->
            EditableRecipeStep(
                step = step,
                idx = index,
                uiEventHandler = addRecipeScreenViewModel
            )
        }
    }
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
    // Would I be able to rig up the viewmodel so that I can collect changes to the step, as opposed to entire state in parent?
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