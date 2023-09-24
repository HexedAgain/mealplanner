package com.example.sharedtest.domain.recipe.usecase

import com.example.data.repository.recipe.RecipeRepository
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.domain.recipe.usecase.InsertRecipeUseCaseImpl
import com.example.sharedtest.data.repository.RecipeRepositoryFactory

class InsertRecipeUseCaseFactory {
    lateinit var repository: RecipeRepository
    fun createForTest(otherRecipeRepository: RecipeRepository? = null): InsertRecipeUseCase {
        repository = otherRecipeRepository ?: RecipeRepositoryFactory().createForTest()
        return InsertRecipeUseCaseImpl(recipeRepository = repository)
    }
}