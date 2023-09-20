package com.example.data.database.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.core.AppDatabase
import com.example.data.database.model.dao.RecipeDao
import com.example.data.database.model.dao.StepDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "Recipes"
        ).build()
    }

    @Provides
    @Singleton
    fun providesStepDao(
        appDatabase: AppDatabase
    ): StepDao {
        return appDatabase.stepDao()
    }

    @Provides
    @Singleton
    fun providesRecipeDao(
        appDatabase: AppDatabase
    ): RecipeDao {
        return appDatabase.recipeDao()
    }
}