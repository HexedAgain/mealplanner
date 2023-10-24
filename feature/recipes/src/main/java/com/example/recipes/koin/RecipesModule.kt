package com.example.recipes.koin

import com.example.recipes.viewmodel.RecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val recipesModule = module {
    viewModel {
        RecipesViewModel(getRecipesUseCase = get(), navigator = get())
    }
}