package com.example.data.database.model.dao

import androidx.room.*
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep
import com.example.data.database.model.relation.DbRecipeWithSteps

@Dao
interface RecipeDao {
    @Transaction
    @Query("SELECT * FROM DbRecipe LIMIT :count")
    fun getRecipes(count: Int): List<DbRecipeWithSteps>

    @Insert
    fun insertRecipe(recipe: DbRecipe): Long

    @Insert
    fun insertSteps(steps: List<DbStep>)

    @Transaction
    @Insert
    fun insertRecipeAndSteps(recipe: DbRecipe, steps: List<DbStep>) {
        val recipeId = insertRecipe(recipe)
        insertSteps (steps.map {
            it.copy(recipeId = recipeId)
        })
    }

    @Delete
    fun deleteRecipe(recipe: DbRecipe)

    @Update
    fun updateRecipe(updatedRecipe: DbRecipe)
}