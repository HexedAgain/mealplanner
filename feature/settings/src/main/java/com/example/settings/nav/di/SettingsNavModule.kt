package com.example.settings.nav.di

import com.example.core.navigation.BottomNavigationItem
import com.example.settings.nav.SettingsNavScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
class SettingsNavModule {
    @Provides
    @IntoSet
    fun providesSettingsNavScreen(): BottomNavigationItem {
        return SettingsNavScreen()
    }
}