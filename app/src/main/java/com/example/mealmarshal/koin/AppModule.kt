package com.example.mealmarshal.koin

import com.example.core.koin.coreModule
import com.example.core.navigation.Navigator
import com.example.core.navigation.NavigatorImpl
import com.example.lab.koin.labModule
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import com.example.recipes.koin.recipesModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    includes(labModule, recipesModule, coreModule)
    single(named("ApplicationContext")) {
        androidContext()
    }
    single {
        NavigatorImpl(scope = get(qualifier = named("MainScope"))) as Navigator
    }
    viewModel {
        MainScreenViewModel(bottomNavItems = get(), screens = get())
    }
}