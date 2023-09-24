package com.example.domain.recipe.usecase

import com.example.data.database.model.relation.DbRecipeWithSteps
import com.example.data.repository.recipe.RecipeRepository
import com.example.domain.core.mapper.Mapper
import com.example.core.state.State
import com.example.domain.recipe.model.Recipe
import javax.inject.Inject

class GetRecipesUseCaseImpl @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val mapper: Mapper<List<DbRecipeWithSteps>, List<Recipe>>
): GetRecipesUseCase {
    override suspend fun invoke(): State<List<Recipe>> {
        val results = recipeRepository.getRecipesLocally()
        return State.Success(mapper(results))
    }
}