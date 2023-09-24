package com.example.domain.recipe.usecase

import com.example.data.database.model.dao.RecipeDao
import com.example.data.repository.recipe.RecipeRepository
import com.example.domain.recipe.model.RecipeStep

class InsertRecipeUseCaseImpl(
    private val recipeRepository: RecipeRepository,
): InsertRecipeUseCase {
    override suspend fun invoke(arg: RecipeStep) {
        TODO("Not yet implemented")
    }
}