package com.example.domain.recipe.mapper.di

import com.example.data.database.model.relation.DbRecipeWithSteps
import com.example.domain.core.mapper.Mapper
import com.example.domain.recipe.mapper.DbRecipeToRecipeMapper
import com.example.domain.recipe.model.Recipe
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
//
//@Module
//@InstallIn(SingletonComponent::class)
//abstract class MapperModule {
//    @Binds
//    abstract fun bindsDbRecipeToRecipeMapper(
//        mapper: DbRecipeToRecipeMapper
//    ): Mapper<List<DbRecipeWithSteps>, List<Recipe>>
//}

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    fun providesDbRecipeToRecipeMapper(
    ): Mapper<List<DbRecipeWithSteps>, List<Recipe>> {
        return DbRecipeToRecipeMapper()
    }
}
