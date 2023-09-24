package com.example.sharedtest.data.repository

import com.example.data.database.model.dao.RecipeDao
import com.example.data.repository.recipe.RecipeRepository
import com.example.data.repository.recipe.RecipeRepositoryImpl
import com.example.sharedtest.data.database.model.dao.MockRecipeDao

fun RecipeRepositoryImpl.createForTest(): RecipeRepository {
    return RecipeRepositoryImpl(MockRecipeDao())
}

class RecipeRepositoryFactory {
    lateinit var recipeDao: RecipeDao
    fun createForTest(otherRecipeDao: RecipeDao? = null): RecipeRepository {
        recipeDao = otherRecipeDao ?: MockRecipeDao()
        return RecipeRepositoryImpl(recipeDao = recipeDao)
    }
}