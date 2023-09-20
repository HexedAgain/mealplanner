package com.example.domain.recipe.usecase

import com.example.domain.core.usecase.UseCaseWithArg
import com.example.domain.recipe.model.RecipeStep

interface InsertRecipeUseCase: UseCaseWithArg<RecipeStep, Unit>