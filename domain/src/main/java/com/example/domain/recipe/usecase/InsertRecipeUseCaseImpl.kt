package com.example.domain.recipe.usecase

import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep
import com.example.data.repository.recipe.RecipeRepository
import com.example.core.state.State
import com.example.domain.recipe.model.Recipe
import javax.inject.Inject

//class InsertRecipeUseCaseImpl @Inject constructor(
class InsertRecipeUseCaseImpl(
    private val recipeRepository: RecipeRepository,
): InsertRecipeUseCase {
    override suspend fun invoke(arg: Recipe): State<Unit> {
        val dbRecipe = DbRecipe(
            title = arg.title
        )
        val dbSteps = arg.steps.map {
            DbStep(
                title = it.title,
                recipeId = 0, // <-- this is not yet known here
                body = it.body
            )
        }
        return recipeRepository.insertRecipe(dbRecipe, dbSteps).toUIState()
    }
}