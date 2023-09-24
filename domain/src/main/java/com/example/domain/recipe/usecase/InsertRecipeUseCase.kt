package com.example.domain.recipe.usecase

import com.example.core.state.State
import com.example.domain.core.usecase.UseCaseWithArg
import com.example.domain.recipe.model.Recipe
import com.example.domain.recipe.model.RecipeStep

interface InsertRecipeUseCase: UseCaseWithArg<Recipe, State<Unit>>