package com.example.sharedtest.data.database.model.dao

import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.relation.DbRecipeWithSteps

class MockRecipeDao: RecipeDao {
    var didGetRecipes = false
    var countSupplied: Int? = 0
    var getRecipesResponse: List<DbRecipeWithSteps>? = null
    override fun getRecipes(count: Int): List<DbRecipeWithSteps> {
        didGetRecipes = true
        countSupplied = count
        return getRecipesResponse!!
    }

    override fun insertRecipe(recipe: DbRecipe) {
        TODO("Not yet implemented")
    }

    override fun deleteRecipe(recipe: DbRecipe) {
        TODO("Not yet implemented")
    }

    override fun updateRecipe(updatedRecipe: DbRecipe) {
        TODO("Not yet implemented")
    }
}