package com.example.recipes.nav.di

import com.example.core.navigation.BottomNavigationItem
import com.example.recipes.nav.RecipesNavScreen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
class RecipesNavModule {
    @Provides
    @IntoSet
    fun providesRecipesNavScreen(): BottomNavigationItem {
        return RecipesNavScreen()
    }
}