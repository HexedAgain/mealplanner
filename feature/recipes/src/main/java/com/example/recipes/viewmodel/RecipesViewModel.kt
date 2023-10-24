package com.example.recipes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.navigation.Navigator
import com.example.domain.recipe.usecase.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class RecipesViewModel @Inject constructor(
class RecipesViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val navigator: Navigator
):
    Navigator by navigator,
    ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRecipesUseCase.invoke()
        }
    }
}