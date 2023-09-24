package com.example.domain.recipe.usecase

import com.example.core.state.State
import com.example.domain.core.usecase.UseCase
import com.example.domain.recipe.model.Recipe

interface GetRecipesUseCase: UseCase<State<List<Recipe>>>