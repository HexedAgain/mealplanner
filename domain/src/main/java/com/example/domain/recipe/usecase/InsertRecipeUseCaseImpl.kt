package com.example.domain.recipe.usecase

import com.example.core.error.UIRecipeErrorCode
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep
import com.example.data.repository.recipe.RecipeRepository
import com.example.core.state.State
import com.example.domain.recipe.model.Recipe
import java.lang.IllegalStateException
import javax.inject.Inject

//class InsertRecipeUseCaseImpl @Inject constructor(
class InsertRecipeUseCaseImpl(
    private val recipeRepository: RecipeRepository,
): InsertRecipeUseCase {
    override suspend fun invoke(recipe: Recipe): State<Unit> {
        if (recipe.title.isEmpty()) {
            return State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_RECIPE)
        }
        if (recipe.steps.isEmpty()) {
            return State.Error(UIRecipeErrorCode.SAVE_RECIPE_NO_STEPS)
        }
        if (recipe.steps.any { it.title.isEmpty() xor it.body.isEmpty() }) {
            return State.Error(UIRecipeErrorCode.SAVE_RECIPE_INCOMPLETE_STEP)
        }

        val dbRecipe = DbRecipe(
            title = recipe.title
        )
        val titles: HashSet<String> = hashSetOf()
        val dbSteps = recipe.steps
            .filter { !(it.title.isEmpty() && it.body.isEmpty()) }
            .map {
                titles.add(it.title)
                DbStep(
                    title = it.title,
                    recipeId = 0, // <-- this is not yet known here
                    body = it.body
                )
            }

        return if (titles.size < dbSteps.size) {
            return State.Error(UIRecipeErrorCode.SAVE_RECIPE_DUPLICATE_STEP)
        } else {
            recipeRepository.insertRecipe(dbRecipe, dbSteps).toUIState()
        }
    }
}