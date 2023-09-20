package com.example.data.database.model.dao

import androidx.room.*
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.relation.DbRecipeWithSteps

@Dao
interface RecipeDao {
    @Transaction
    @Query("SELECT * FROM DbRecipe LIMIT :count")
    fun getRecipes(count: Int): List<DbRecipeWithSteps>

    @Insert
    fun insertRecipe(recipe: DbRecipe)

    @Delete
    fun deleteRecipe(recipe: DbRecipe)

    @Update
    fun updateRecipe(updatedRecipe: DbRecipe)
}