package com.example.lab.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.core.error.UIRecipeErrorCode
import com.example.domain.core.state.State
import com.example.domain.recipe.model.RecipeStep
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface AddRecipeHandler {
    val recipeTitle: StateFlow<String>
    val currentStep: StateFlow<RecipeStep>
    val currentStepIndex: StateFlow<Int>
    val steps: StateFlow<List<RecipeStep>>
    fun addRecipe()
    fun insertStep(insertAt: Int? = null)
    fun updateRecipeTitle(newTitle: String)
    fun markStepForEdit(stepIndex: Int)
    fun updateStep()
}

@HiltViewModel
class LabScreenViewModel @Inject constructor(
    private val insertRecipeUseCase: InsertRecipeUseCase
):
    AddRecipeHandler,
    ViewModel() {

    @VisibleForTesting
    val _errorState: MutableStateFlow<State<Unit>> = MutableStateFlow(State.Empty())
    val errorState: StateFlow<State<Unit>> = _errorState.asStateFlow()

    @VisibleForTesting
    val _recipeTitle = MutableStateFlow("")
    override val recipeTitle = _recipeTitle.asStateFlow()

    @VisibleForTesting
    val _currentStep = MutableStateFlow(RecipeStep.empty())
    override val currentStep = _currentStep.asStateFlow()
    @VisibleForTesting
    var _currentStepIndex = MutableStateFlow(0)
    override val currentStepIndex = _currentStepIndex.asStateFlow()

    @VisibleForTesting
    val _steps = MutableStateFlow<List<RecipeStep>>(listOf())
    override val steps = _steps.asStateFlow()

    override fun addRecipe() {
        TODO("Not yet implemented")
    }

    override fun updateRecipeTitle(newTitle: String) {
        _recipeTitle.value = newTitle
    }

    override fun markStepForEdit(stepIndex: Int) {
        if (stepIndex < 0 || stepIndex >= _steps.value.size) return
//        _currentStepIndex.value = stepIndex
    }

    override fun updateStep() {

    }

    override fun insertStep(insertAt: Int?) {
        if (insertAt != null && (insertAt < 0 || insertAt > _steps.value.size)) {
            _errorState.value = State.Error(UIRecipeErrorCode.INSERT_STEP_ERROR)
            return
        }
        _steps.value = if (insertAt == null) {
            _steps.value + _currentStep.value
        } else {
            _steps.value.subList(0, insertAt) + _currentStep.value + _steps.value.subList(insertAt, _steps.value.size)
        }
        _currentStep.value = RecipeStep.empty()
    }
}