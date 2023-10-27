package com.example.recipes.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.error.ErrorCode
import com.example.core.navigation.Navigator
import com.example.core.state.State
import com.example.core.ui.Reducer
import com.example.core.ui.UIEvent
import com.example.core.ui.UIEventStateHandler
import com.example.core.ui.UIResult
import com.example.core.ui.UIState
import com.example.core.viewmodel.BaseViewModel
import com.example.domain.recipe.model.Recipe
import com.example.domain.recipe.usecase.GetRecipesUseCase
import kotlinx.coroutines.launch

@Immutable
data class ListRecipesState(
    val recipes: List<Recipe>,
    val uiState: State<Unit>
): UIState {
    companion object {
        fun empty(): ListRecipesState {
            return ListRecipesState(recipes = emptyList(), uiState = State.Empty())
        }
    }
}

sealed class ListRecipesEvent: UIEvent {
    class FetchRecipes(): ListRecipesEvent()
}

sealed class ListRecipesResult: UIResult {
    class FetchRecipes(val recipes: List<Recipe>): ListRecipesResult()
    class Error(val errorCode: ErrorCode): ListRecipesResult()
}

interface ListRecipesStateEventHandler: UIEventStateHandler<ListRecipesState> {
}

class RecipesViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val navigator: Navigator,
    initialState: ListRecipesState = ListRecipesState.empty()
):
    Navigator by navigator,
    ListRecipesStateEventHandler,
    BaseViewModel<ListRecipesState>() {

    private val reducer = ListRecipesReducer(initialState)
    override val state = reducer.state

    override fun postEvent(event: UIEvent) {
        when (event as ListRecipesEvent) {
            is ListRecipesEvent.FetchRecipes -> {
                viewModelScope.launch {
                    val result = getRecipesUseCase.invoke()
                    when (result) {
                        is State.Success -> { reducer.sendResult(ListRecipesResult.FetchRecipes(result.data)) }
                        is State.Error -> { reducer.sendResult(ListRecipesResult.Error(result.errorCode)) }
                        else -> {}
                    }
                }
            }
        }
    }


    private class ListRecipesReducer(initialState: ListRecipesState):
        Reducer<ListRecipesState, ListRecipesEvent, ListRecipesResult>(initialState) {
        override fun reduce(oldState: ListRecipesState, result: ListRecipesResult) {
            when(result) {
                is ListRecipesResult.Error -> { setState(oldState.copy(uiState = State.Error(result.errorCode))) }
                is ListRecipesResult.FetchRecipes -> { setState(oldState.copy(recipes = result.recipes)) }
            }
        }

        override fun reduce(oldState: ListRecipesState, event: ListRecipesEvent) {
            when(event) {
                is ListRecipesEvent.FetchRecipes -> {}
            }
        }
    }
}