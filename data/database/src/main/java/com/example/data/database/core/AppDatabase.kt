package com.example.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.dao.StepDao
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep

@Database(entities = [DbStep::class, DbRecipe::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun stepDao(): StepDao
    abstract fun recipeDao(): RecipeDao
}