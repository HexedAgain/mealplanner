package com.example.domain.koin

import com.example.data.database.model.relation.DbRecipeWithSteps
import com.example.data.repository.koin.repositoryModule
import com.example.domain.core.mapper.Mapper
import com.example.domain.recipe.mapper.DbToDomainRecipeMapper
import com.example.domain.recipe.model.Recipe
import com.example.domain.recipe.usecase.GetRecipesUseCase
import com.example.domain.recipe.usecase.GetRecipesUseCaseImpl
import com.example.domain.recipe.usecase.InsertRecipeUseCase
import com.example.domain.recipe.usecase.InsertRecipeUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    includes(repositoryModule)
    single {
        InsertRecipeUseCaseImpl(recipeRepository = get()) as InsertRecipeUseCase
    }
    single {
        GetRecipesUseCaseImpl(recipeRepository = get(), mapper = get()) as GetRecipesUseCase
    }
    single {
        DbToDomainRecipeMapper() as Mapper<List<DbRecipeWithSteps>, List<Recipe>>
    }
}