package com.example.domain.recipe.usecase.di

import com.example.domain.recipe.usecase.GetRecipesUseCase
import com.example.domain.recipe.usecase.GetRecipesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindsGetRecipesUseCase(
        getRecipesUseCaseImpl: GetRecipesUseCaseImpl
    ): GetRecipesUseCase
}