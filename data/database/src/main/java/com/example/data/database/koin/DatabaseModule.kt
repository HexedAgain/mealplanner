package com.example.data.database.koin

import android.content.Context
import androidx.room.Room
import com.example.data.database.core.AppDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
    single {
        val database = get<AppDatabase>()
        database.stepDao()
    }

    single {
        val database = get<AppDatabase>()
        database.recipeDao()
    }

    single {
        Room.databaseBuilder(
            get(qualifier = named("ApplicationContext")),
            AppDatabase::class.java,
            "Recipes"
        ).build()
    }
}