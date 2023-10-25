package com.example.mealmarshal.koin

import com.example.core.koin.coreModule
import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.NavScreen
import com.example.core.navigation.Navigator
import com.example.core.navigation.NavigatorImpl
import com.example.lab.koin.labModule
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import com.example.recipes.koin.recipesModule
import com.example.settings.koin.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    includes(labModule, recipesModule, settingsModule, coreModule)
    single(named("ApplicationContext")) {
        androidContext()
    }
    single {
        NavigatorImpl(scope = get(qualifier = named("MainScope"))) as Navigator
    }
    viewModel {
        val bottomNavItems: List<BottomNavigationItem> = getAll()
        val screens: List<NavScreen> = getAll()
        MainScreenViewModel(bottomNavItems = bottomNavItems.toMutableSet(), screens = screens.toMutableSet())
        //MainScreenViewModel(bottomNavItems = mutableSetOf(), screens = mutableSetOf())
//        MainScreenViewModel(test = "", bottomNavItems = mutableSetOf())
    }
}