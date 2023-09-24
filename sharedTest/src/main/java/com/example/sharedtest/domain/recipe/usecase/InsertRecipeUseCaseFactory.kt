package com.example.sharedtest.domain.recipe.usecase

import com.example.data.repository.recipe.RecipeRepository
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.domain.recipe.usecase.InsertRecipeUseCaseImpl
import com.example.sharedtest.data.repository.RecipeRepositoryFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class InsertRecipeUseCaseFactory(
    val dispatcher: CoroutineDispatcher
) {
    lateinit var repository: RecipeRepository
    lateinit var repositoryFactory: RecipeRepositoryFactory
    fun createForTest(otherRecipeRepository: RecipeRepository? = null): InsertRecipeUseCase {
        repositoryFactory = RecipeRepositoryFactory(dispatcher)
        repository = otherRecipeRepository ?: repositoryFactory.createForTest()
        return InsertRecipeUseCaseImpl(recipeRepository = repository)
    }
}