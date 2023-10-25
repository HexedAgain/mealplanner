package com.example.data.repository.recipe

import com.example.core.error.DatabaseErrorCode
import com.example.core.state.RepositoryState
import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep
import com.example.data.database.model.relation.DbRecipeWithSteps
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val ioDispatcher: CoroutineDispatcher
): RecipeRepository {
    override suspend fun getRecipesLocally(): List<DbRecipeWithSteps> {
        // FIXME - need some general machinery to catch exceptions and run this on the io thread
        val results = recipeDao.getRecipes(100)
        return results
    }

    override suspend fun insertRecipe(
        dbRecipe: DbRecipe,
        dbSteps: List<DbStep>
    ): RepositoryState<Unit> {
        return withContext(ioDispatcher) {
            try {
                recipeDao.insertRecipeAndSteps(dbRecipe, dbSteps)
                RepositoryState.Success(Unit)
            } catch (ex: java.lang.Exception) {
                val newEx = ex
                if (newEx.message == "") {
                    RepositoryState.Error(DatabaseErrorCode.INSERT_ERROR)
                } else {
                    RepositoryState.Error(DatabaseErrorCode.INSERT_ERROR)
                }
            }
        }
    }
}