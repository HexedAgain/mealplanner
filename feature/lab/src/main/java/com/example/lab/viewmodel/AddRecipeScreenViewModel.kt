package com.example.lab.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.example.core.error.UIRecipeErrorCode
import com.example.core.navigation.Navigator
import com.example.core.state.State
import com.example.core.ui.Reducer
import com.example.core.ui.UIEvent
import com.example.core.ui.UIState
import com.example.core.ui.UIEventStateHandler
import com.example.core.viewmodel.BaseViewModel
import com.example.domain.recipe.model.Recipe
import com.example.domain.recipe.model.RecipeStep
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.lab.viewmodel.event.AddRecipeUIEvent
import com.example.lab.viewmodel.event.AddRecipeUIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
data class AddRecipeState(
    val uiState: State<Unit>,
    val recipeTitle: String,
    val currentStep: RecipeStep, // FIXME - don't need this item
    val steps: List<RecipeStep>
): UIState {
    companion object {
        fun default(): AddRecipeState {
            return AddRecipeState(State.Empty(), "", RecipeStep.empty(), listOf(RecipeStep.empty()))
        }
        fun empty(): AddRecipeState {
            return AddRecipeState(State.Empty(), "", RecipeStep.empty(), listOf())
        }
    }
}

interface AddRecipeStateEventHandler: UIEventStateHandler<AddRecipeState> {
    val steps: StateFlow<List<RecipeStep>>
}

// FIXME There should be an AddRecipeViewModel to be honest and an associated screen (no navbar)
class AddRecipeScreenViewModel(
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val navigator: Navigator,
    initialState: AddRecipeState = AddRecipeState.default()
):
    Navigator by navigator,
    AddRecipeStateEventHandler,
    BaseViewModel<AddRecipeState>() {

    private val reducer = AddRecipeReducer(initialState)

    override val steps: StateFlow<List<RecipeStep>>
        get() = MutableStateFlow(reducer.state.value.steps).asStateFlow()

    override val state: StateFlow<AddRecipeState>
        get() = reducer.state

    override fun postEvent(event: UIEvent) {
        when (event as AddRecipeUIEvent) {
            is AddRecipeUIEvent.DismissError,
            is AddRecipeUIEvent.UpdateRecipeTitle,
            is AddRecipeUIEvent.UpdateStep,
            is AddRecipeUIEvent.DeleteStep,
            is AddRecipeUIEvent.InsertStep, // maybe want insert before, insert after
            is AddRecipeUIEvent.MarkStepForEdit -> reducer.sendEvent(event)
            is AddRecipeUIEvent.SaveRecipe -> { saveRecipe() }
        }
    }

    private fun saveRecipe() {
        viewModelScope.launch {
            val result = with (state.value) { insertRecipeUseCase(Recipe(title = recipeTitle, steps = steps)) }
            when (result) {
                is State.Success -> reducer.sendResult(AddRecipeUIResult.SaveRecipe())
                is State.Error -> reducer.sendResult(AddRecipeUIResult.Error(result.errorCode))
                else -> {}
            }
        }
    }

    private class AddRecipeReducer(initialState: AddRecipeState):
        Reducer<AddRecipeState, AddRecipeUIEvent, AddRecipeUIResult>(initialState) {
        override fun reduce(oldState: AddRecipeState, result: AddRecipeUIResult) {
            when (result) {
                is AddRecipeUIResult.SaveRecipe -> {
                    setState(AddRecipeState.default())
                }
                is AddRecipeUIResult.Error -> {
                    setState(oldState.copy(uiState = State.Error(result.errorCode)))
                }
            }
        }
        override fun reduce(oldState: AddRecipeState, event: AddRecipeUIEvent) {
            when (event) {
                is AddRecipeUIEvent.DismissError -> {
                    setState(oldState.copy(uiState = State.Empty()))
                }
                is AddRecipeUIEvent.UpdateRecipeTitle -> {
                    setState(oldState.copy(recipeTitle = event.newTitle))
                }
                is AddRecipeUIEvent.MarkStepForEdit -> {
                    state.value.steps.getOrNull(event.stepIndex)?.let {
                        setState(oldState.copy(currentStep = it))
                    } ?: run {
                        setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.EDIT_STEP_OUT_OF_BOUNDS)))
                    }
                }
                is AddRecipeUIEvent.UpdateStep -> {
                    if (event.updateAt < 0 || event.updateAt >= oldState.steps.size) {
                        setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.UPDATE_STEP_NOT_FOUND)))
                    } else {
                        val currentStep = oldState.steps[event.updateAt]
                        val newStep = RecipeStep(
                            title = event.title ?: currentStep.title,
                            body = event.body ?: currentStep.body,
                        )
                        val newSteps = state.value.steps.toMutableList()
                        newSteps[event.updateAt] = newStep
                        setState(oldState.copy(steps = newSteps))
                    }
                }
                is AddRecipeUIEvent.DeleteStep -> {
                    val stepToDelete = state.value.steps.getOrNull(event.deleteAt)
                    if (stepToDelete == null) {
                        setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.DELETE_STEP_NOT_FOUND)))
                    } else {
                        setState(oldState.copy(steps = oldState.steps.filter { it.title != stepToDelete.title }))
                    }
                }
                is AddRecipeUIEvent.InsertStep -> {
                    with (state.value) {
                        val insertAt = event.insertAt
                        if (insertAt != null && (insertAt < 0 || insertAt > steps.size)) {
                            setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.INSERT_STEP_OUT_OF_BOUNDS)))
                            return
                        }
                        val newSteps = if (insertAt == null) {
                            steps + currentStep
                        } else {
                            steps.subList(0, insertAt) + currentStep + steps.subList(insertAt, steps.size)
                        }
                        setState(oldState.copy(
                            steps = newSteps,
                            currentStep = RecipeStep.empty()
                        ))
                    }
                }
                AddRecipeUIEvent.SaveRecipe -> {}
            }
        }
    }
}