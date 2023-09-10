package com.example.settings.nav.di

import com.example.core.navigation.BottomNavigationItem
import com.example.settings.nav.SettingsNavScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsNavModule {
    @Binds
    @IntoSet
    abstract fun providesSettingsNavScreen(
        settingsNavScreen: SettingsNavScreen
    ): BottomNavigationItem
}