package com.example.domain.recipe.usecase

import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.relation.DbRecipeWithSteps
import com.example.domain.core.mapper.Mapper
import com.example.domain.core.state.State
import com.example.domain.recipe.model.Recipe
import javax.inject.Inject

class GetRecipesUseCaseImpl @Inject constructor(
    private val recipeDao: RecipeDao,
    private val mapper: Mapper<List<DbRecipeWithSteps>, List<Recipe>>
): GetRecipesUseCase {
    // FIXME - should do this in repository. Need to catch exceptions
    override suspend fun invoke(): State<List<Recipe>> {
        val results = recipeDao.getRecipes(100)
        return State.Success(mapper(results))
    }
}