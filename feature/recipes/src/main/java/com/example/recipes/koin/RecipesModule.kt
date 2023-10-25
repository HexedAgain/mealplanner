package com.example.recipes.koin

import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.NavScreen
import com.example.recipes.nav.RecipesNavScreen
import com.example.recipes.viewmodel.RecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val recipesModule = module {
    viewModel {
        RecipesViewModel(getRecipesUseCase = get(), navigator = get())
    }
    single { RecipesNavScreen() } bind BottomNavigationItem::class
}