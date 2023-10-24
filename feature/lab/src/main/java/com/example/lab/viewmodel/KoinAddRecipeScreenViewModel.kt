package com.example.lab.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.domain.recipe.usecase.KoinInsertRecipeUseCase

class KoinAddRecipeScreenViewModel(
    private val insertRecipeUseCase: KoinInsertRecipeUseCase,
): ViewModel() {
    fun foo() {}
}