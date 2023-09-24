package com.example.sharedtest.domain.recipe.model

import com.example.domain.recipe.model.RecipeStep

object RecipeStepMocks {
    val recipeStep = RecipeStep("some-recipe-title", "some-recipe-body")
    val otherRecipeStep = RecipeStep("some-other-recipe-title", "some-other-recipe-body")
    val yetAnotherRecipeStep = RecipeStep("some-other-recipe-title-2", "some-other-recipe-body-2")
}