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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Immutable
data class AddRecipeState(
    val uiState: State<Unit>,
    val recipeTitle: String,
    val currentStep: RecipeStep,
    val steps: List<RecipeStep>
): UIState {
    companion object {
        fun empty(): AddRecipeState {
            return AddRecipeState(State.Empty(), "", RecipeStep.empty(), listOf())
        }
    }
}

// FIXME There should be an AddRecipeViewModel to be honest and an associated screen (no navbar)
class AddRecipeScreenViewModel(
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val navigator: Navigator,
    initialState: AddRecipeState = AddRecipeState.empty()
):
    Navigator by navigator,
    UIEventStateHandler<AddRecipeState>,
    BaseViewModel<AddRecipeState>() {

    private val reducer = AddRecipeReducer(initialState)

    override val state: StateFlow<AddRecipeState>
        get() = reducer.state

    override fun postEvent(event: UIEvent) {
        when (event as AddRecipeUIEvent) {
            is AddRecipeUIEvent.UpdateRecipeTitle,
            is AddRecipeUIEvent.UpdateStep,
            is AddRecipeUIEvent.DeleteStep,
            is AddRecipeUIEvent.InsertStep, // maybe want insert before, insert after
            is AddRecipeUIEvent.MarkStepForEdit -> reducer.sendEvent(event)
            is AddRecipeUIEvent.SaveRecipe -> { saveRecipe() }
        }
    }

    private fun saveRecipe() {
        if (state.value.recipeTitle.isEmpty()) {
            reducer.sendResult(AddRecipeUIResult.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_RECIPE))
            return
        }
        if (state.value.steps.isEmpty()) {
            reducer.sendResult(AddRecipeUIResult.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_STEPS))
            return
        }

        viewModelScope.launch {
            val result = with (state.value) { insertRecipeUseCase(Recipe(title = recipeTitle, steps = steps)) }
            if (result is State.Success) {
                reducer.sendResult(AddRecipeUIResult.SaveRecipe())
            } else {
                reducer.sendResult(AddRecipeUIResult.Error(UIRecipeErrorCode.SAVE_RECIPE_DB_ERROR))
            }
        }
    }

    private class AddRecipeReducer(initialState: AddRecipeState):
        Reducer<AddRecipeState, AddRecipeUIEvent, AddRecipeUIResult>(initialState) {
        override fun reduce(oldState: AddRecipeState, result: AddRecipeUIResult) {
            when (result) {
                is AddRecipeUIResult.SaveRecipe -> {
                    setState(AddRecipeState.empty())
                }
                is AddRecipeUIResult.Error -> {
                    setState(oldState.copy(uiState = State.Error(result.errorCode)))
                }
            }
        }
        override fun reduce(oldState: AddRecipeState, event: AddRecipeUIEvent) {
            when (event) {
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
                    val idx = state.value.steps.indexOfFirst { it.title == state.value.currentStep.title }
                    if (idx == -1) {
                        setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.UPDATE_STEP_NOT_FOUND)))
                    } else {
                        val newSteps = state.value.steps.toMutableList()
                        newSteps[idx] = state.value.currentStep
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
                    val insertAt = event.insertAt
                    if (insertAt != null && (insertAt < 0 || insertAt > state.value.steps.size)) {
                        setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.INSERT_STEP_OUT_OF_BOUNDS)))
                        return
                    }
                    if (state.value.steps.find { it.title == state.value.currentStep.title } != null) {
                        setState(oldState.copy(uiState = State.Error(UIRecipeErrorCode.INSERT_STEP_DUPLICATE)))
                        return
                    }
                    val newSteps = if (insertAt == null) {
                        state.value.steps + state.value.currentStep
                    } else {
                        state.value.steps.subList(0, insertAt) + state.value.currentStep + state.value.steps.subList(insertAt, state.value.steps.size)
                    }
                    setState(oldState.copy(
                        steps = newSteps,
                        currentStep = RecipeStep.empty()
                    ))
                }
                AddRecipeUIEvent.SaveRecipe -> {}
            }
        }
    }
}