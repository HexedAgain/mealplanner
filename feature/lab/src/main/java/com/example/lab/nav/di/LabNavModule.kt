package com.example.lab.nav.di

import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.NavScreen
import com.example.lab.nav.AddRecipeNavScreen
import com.example.lab.nav.LabNavScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class LabNavModule {
    @Binds
    @IntoSet
    abstract fun providesLabNavScreen(labNavScreen: LabNavScreen): BottomNavigationItem

    @Binds
    @IntoSet
    abstract fun bindsAddRecipeScreen(addRecipeNavScreen: AddRecipeNavScreen): NavScreen
}