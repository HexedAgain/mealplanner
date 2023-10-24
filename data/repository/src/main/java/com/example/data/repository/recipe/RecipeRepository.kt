package com.example.data.repository.recipe

import com.example.core.state.RepositoryState
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep
import com.example.data.database.model.relation.DbRecipeWithSteps

interface RecipeRepository {
    suspend fun getRecipesLocally(): List<DbRecipeWithSteps>
    suspend fun insertRecipe(dbRecipe: DbRecipe, dbSteps: List<DbStep>): RepositoryState<Unit>
}