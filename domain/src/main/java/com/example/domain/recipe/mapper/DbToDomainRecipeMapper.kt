package com.example.domain.recipe.mapper

import com.example.data.database.model.relation.DbRecipeWithSteps
import com.example.domain.core.mapper.Mapper
import com.example.domain.recipe.model.Recipe
import com.example.domain.recipe.model.RecipeStep
import javax.inject.Inject

class DbToDomainRecipeMapper @Inject constructor(): Mapper<List<DbRecipeWithSteps>, List<Recipe>> {
    override fun invoke(input: List<DbRecipeWithSteps>): List<Recipe> {
        return input.map { recipeWithSteps ->
            Recipe(
                title = recipeWithSteps.recipe.title,
                steps = recipeWithSteps.steps.map {
                    RecipeStep(
                        title = it.title,
                        body = it.body,
                    )
                }
            )
        }
    }
}