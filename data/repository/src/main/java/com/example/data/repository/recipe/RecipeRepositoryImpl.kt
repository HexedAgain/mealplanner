package com.example.data.repository.recipe

import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.relation.DbRecipeWithSteps
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao,
): RecipeRepository {
    override suspend fun getRecipes(): List<DbRecipeWithSteps> {
        // FIXME - need some general machinery to catch exceptions and run this on the io thread
        val results = recipeDao.getRecipes(100)
        return results
    }
}