package com.example.lab.koin

import com.example.domain.koin.domainModule
import com.example.domain.recipe.usecase.KoinInsertRecipeUseCase
import com.example.domain.recipe.usecase.KoinInsertRecipeUseCaseImpl
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.KoinAddRecipeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

//val labModule = module {
//    singleOf(::InsertRecipeUseCaseImpl)
//}

val labModule = module {
    includes(domainModule)
    viewModel {
        KoinAddRecipeScreenViewModel(get())
    }
    viewModel {
        AddRecipeScreenViewModel(
            insertRecipeUseCase = get(),
            navigator = get()
        )
    }
}
