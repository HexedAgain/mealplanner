package com.example.sharedtest.data.database.model.dao

import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep
import com.example.data.database.model.relation.DbRecipeWithSteps

class MockRecipeDao: RecipeDao {
    var didGetRecipes = false
    var countSupplied: Int? = 0
    var getRecipesResponse: List<DbRecipeWithSteps>? = null
    var shouldThrowException = false
    override fun getRecipes(count: Int): List<DbRecipeWithSteps> {
        didGetRecipes = true
        countSupplied = count
        return getRecipesResponse!!
    }


    var didInsertRecipe = false
    var recipeSupplied: DbRecipe? = null
    var insertRecipeResponse: Long? = null
    override fun insertRecipe(recipe: DbRecipe): Long {
        didInsertRecipe = true
        recipeSupplied = recipe

        if (shouldThrowException) throw java.lang.IllegalStateException("some-exception")
        return insertRecipeResponse!!
    }

    var didInsertSteps = false
    var stepsSupplied: List<DbStep>? = null
    override fun insertSteps(steps: List<DbStep>) {
        didInsertSteps = true
        stepsSupplied = steps

        if (shouldThrowException) throw java.lang.IllegalStateException("some-exception")
    }

//    var didInsertRecipeAndSteps = false
//    override fun insertRecipeAndSteps(recipe: DbRecipe, steps: List<DbStep>) {
//        didInsertRecipeAndSteps = true
//        recipeSupplied = recipe
//        stepsSupplied = steps
//
//        if (shouldThrowException) throw java.lang.IllegalStateException("some-exception")
//    }

    override fun deleteRecipe(recipe: DbRecipe) {
        TODO("Not yet implemented")
    }

    override fun updateRecipe(updatedRecipe: DbRecipe) {
        TODO("Not yet implemented")
    }
}