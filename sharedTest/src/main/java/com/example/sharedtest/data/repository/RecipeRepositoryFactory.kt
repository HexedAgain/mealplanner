package com.example.sharedtest.data.repository

import com.example.data.database.model.dao.RecipeDao
import com.example.data.repository.recipe.RecipeRepository
import com.example.data.repository.recipe.RecipeRepositoryImpl
import com.example.sharedtest.data.database.model.dao.MockRecipeDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class RecipeRepositoryFactory(
    val dispatcher: CoroutineDispatcher
) {
    lateinit var recipeDao: RecipeDao
    fun createForTest(otherRecipeDao: RecipeDao? = null): RecipeRepository {
        recipeDao = otherRecipeDao ?: MockRecipeDao()
        return RecipeRepositoryImpl(recipeDao = recipeDao, dispatcher)
    }
}