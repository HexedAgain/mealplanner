package com.example.lab.koin

import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.NavScreen
import com.example.domain.koin.domainModule
import com.example.lab.nav.AddRecipeNavScreen
import com.example.lab.nav.LabNavScreen
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.KoinAddRecipeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.binds
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
    single { AddRecipeNavScreen() } bind NavScreen::class
    single { LabNavScreen() } bind BottomNavigationItem::class
}
