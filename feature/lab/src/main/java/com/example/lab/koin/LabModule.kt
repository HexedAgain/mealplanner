package com.example.lab.koin

import com.example.domain.recipe.usecase.KoinInsertRecipeUseCase
import com.example.domain.recipe.usecase.KoinInsertRecipeUseCaseImpl
import com.example.lab.viewmodel.KoinAddRecipeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//val labModule = module {
//    singleOf(::InsertRecipeUseCaseImpl)
//}

val labModule = module {
    single {
        KoinInsertRecipeUseCaseImpl() as KoinInsertRecipeUseCase
    }
    viewModel {
        KoinAddRecipeScreenViewModel(get())
    }
}
