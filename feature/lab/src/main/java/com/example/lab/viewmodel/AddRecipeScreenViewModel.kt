package com.example.lab.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.error.UIRecipeErrorCode
import com.example.core.navigation.Navigator
import com.example.core.state.State
import com.example.core.ui.ViewEvent
import com.example.core.ui.ViewEventHandler
import com.example.core.ui.ViewStateHolder
import com.example.domain.recipe.model.Recipe
import com.example.domain.recipe.model.RecipeStep
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.lab.viewmodel.event.AddRecipeViewEvent
import com.example.lab.viewmodel.event.AddRecipeViewEvent.UpdateRecipeTitle
import com.example.lab.viewmodel.state.AddRecipeViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ViewEventHistory<T: ViewEvent> {
    val events: MutableList<T>
}

interface AddRecipeHandler: ViewEventHandler, ViewStateHolder<AddRecipeViewState>, ViewEventHistory<AddRecipeViewEvent> {
    val recipeTitle: StateFlow<String>
    val currentStep: StateFlow<RecipeStep>
    val steps: StateFlow<List<RecipeStep>>
    fun addRecipe()
    fun insertStep(insertAt: Int? = null)
//    fun updateRecipeTitle(newTitle: String)
    fun markStepForEdit(stepIndex: Int)
    fun updateStep()
    fun deleteStep(deleteAt: Int)
    fun saveRecipe()
}

// FIXME There should be an AddRecipeViewModel to be honest and an associated screen (no navbar)
//@HiltViewModel
//class AddRecipeScreenViewModel @Inject constructor(
class AddRecipeScreenViewModel(
    private val insertRecipeUseCase: InsertRecipeUseCase,
    private val navigator: Navigator
):
    Navigator by navigator,
    AddRecipeHandler,
    ViewModel() {

    @VisibleForTesting
    val _uiState: MutableStateFlow<State<Unit>> = MutableStateFlow(State.Empty())
    val uiState: StateFlow<State<Unit>> = _uiState.asStateFlow()

    override val events = mutableListOf<AddRecipeViewEvent>()

    @VisibleForTesting
    val _recipeTitle = MutableStateFlow("")
    override val recipeTitle = _recipeTitle.asStateFlow()

    @VisibleForTesting
    val _currentStep = MutableStateFlow(RecipeStep.empty())
    override val currentStep = _currentStep.asStateFlow()

    @VisibleForTesting
    val _steps = MutableStateFlow<List<RecipeStep>>(listOf())
    override val steps = _steps.asStateFlow()

    override fun addRecipe() {
        TODO("Not yet implemented")
    }

//    override fun updateRecipeTitle(newTitle: String) {
//        _recipeTitle.value = newTitle
//    }

    override fun markStepForEdit(stepIndex: Int) {
        _currentStep.value = _steps.value.getOrNull(stepIndex) ?: run {
            _uiState.value = State.Error(UIRecipeErrorCode.EDIT_STEP_OUT_OF_BOUNDS)
            return
        }
    }

    override fun updateStep() {
        val idx = _steps.value.indexOfFirst { it.title == currentStep.value.title }
        if (idx == -1) {
            _uiState.value = State.Error(UIRecipeErrorCode.UPDATE_STEP_NOT_FOUND)
        } else {
            val newSteps = _steps.value.toMutableList()
            newSteps[idx] = _currentStep.value
            _steps.value = newSteps
        }
    }

    override fun deleteStep(deleteAt: Int) {
        val stepToDelete = _steps.value.getOrNull(deleteAt) ?: run {
            _uiState.value = State.Error(UIRecipeErrorCode.DELETE_STEP_NOT_FOUND)
            return
        }

        _steps.value = _steps.value.filter { it.title != stepToDelete.title }
    }

    override fun insertStep(insertAt: Int?) {
        if (insertAt != null && (insertAt < 0 || insertAt > _steps.value.size)) {
            _uiState.value = State.Error(UIRecipeErrorCode.INSERT_STEP_OUT_OF_BOUNDS)
            return
        }
        if (_steps.value.find { it.title == currentStep.value.title } != null) {
            _uiState.value = State.Error(UIRecipeErrorCode.INSERT_STEP_DUPLICATE)
            return
        }
        _steps.value = if (insertAt == null) {
            _steps.value + _currentStep.value
        } else {
            _steps.value.subList(0, insertAt) + _currentStep.value + _steps.value.subList(insertAt, _steps.value.size)
        }
        _currentStep.value = RecipeStep.empty()
    }

    override fun saveRecipe() {
        if (_recipeTitle.value.isEmpty()) {
            _uiState.value = State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_RECIPE)
            return
        }
        if (_steps.value.isEmpty()) {
            _uiState.value = State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_STEPS)
            return
        }

        viewModelScope.launch {
            val result = insertRecipeUseCase(Recipe(title = recipeTitle.value, steps = steps.value))
            if (result is State.Success) {
                _steps.value = emptyList()
                _recipeTitle.value = ""
            } else {
                _uiState.value = State.Error(UIRecipeErrorCode.SAVE_RECIPE_DB_ERROR)
            }
        }
    }

    fun updateRecipeTitle(event: UpdateRecipeTitle) {
        updateState(viewState.recipeTitle) { recipeTitle ->
            recipeTitle.update { it.copy(title = event.newTitle) }
        }
    }

    override fun postEvent(event: ViewEvent) {
        when (event as AddRecipeViewEvent) {
            is UpdateRecipeTitle -> updateRecipeTitle(event as UpdateRecipeTitle)
        }
        events += event // In any real setting this could be a GPDR risk if we logged it
    }

    override val viewState = AddRecipeViewState()
}