package com.example.domain.recipe.usecase

import com.example.data.repository.recipe.KoinRecipeRepository

class KoinInsertRecipeUseCaseImpl(
    private val koinRecipeRepository: KoinRecipeRepository
): KoinInsertRecipeUseCase {
}