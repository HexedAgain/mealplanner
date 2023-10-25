package com.example.settings.koin

import com.example.core.koin.coreModule
import com.example.core.navigation.BottomNavigationItem
import com.example.settings.nav.SettingsNavScreen
import com.example.settings.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val settingsModule = module {
    viewModel {
        SettingsViewModel(navigator = get())
    }
    single { SettingsNavScreen() } bind BottomNavigationItem::class
}