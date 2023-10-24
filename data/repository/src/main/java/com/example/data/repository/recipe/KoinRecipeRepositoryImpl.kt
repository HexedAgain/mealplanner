package com.example.data.repository.recipe

import com.example.data.database.model.dao.RecipeDao
import kotlinx.coroutines.CoroutineDispatcher

class KoinRecipeRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val recipeDao: RecipeDao,
): KoinRecipeRepository {
}