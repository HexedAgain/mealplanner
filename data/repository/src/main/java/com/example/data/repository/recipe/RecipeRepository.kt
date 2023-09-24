package com.example.data.repository.recipe

import com.example.data.database.model.relation.DbRecipeWithSteps

interface RecipeRepository {
    suspend fun getRecipesLocally(): List<DbRecipeWithSteps>
}