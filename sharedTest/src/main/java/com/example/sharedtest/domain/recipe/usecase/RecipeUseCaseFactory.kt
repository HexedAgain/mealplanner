package com.example.sharedtest.domain.recipe.usecase

import com.example.data.repository.recipe.RecipeRepository
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.domain.recipe.usecase.InsertRecipeUseCaseImpl
import com.example.sharedtest.data.repository.RecipeRepositoryFactory

fun InsertRecipeUseCase.create(): InsertRecipeUseCase {
    return InsertRecipeUseCaseImpl(RecipeRepositoryFactory().createForTest())
}

class RecipeUseCaseFactory {
}