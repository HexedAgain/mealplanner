package com.example.recipes.nav.di

import com.example.core.navigation.BottomNavigationItem
import com.example.recipes.nav.RecipesNavScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class RecipesNavModule {
    @Binds
    @IntoSet
    abstract fun providesRecipesNavScreen(recipesNavScreen: RecipesNavScreen): BottomNavigationItem
}