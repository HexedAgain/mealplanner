package com.example.data.repository.koin

import com.example.data.database.koin.databaseModule
import com.example.data.repository.recipe.RecipeRepository
import com.example.data.repository.recipe.RecipeRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    includes(databaseModule)
    factory {
        RecipeRepositoryImpl(
            ioDispatcher = get(qualifier = named("IODispatcher")),
            recipeDao = get()
        ) as RecipeRepository
    }
}