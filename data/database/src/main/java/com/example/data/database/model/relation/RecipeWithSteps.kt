package com.example.data.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.database.model.entity.Recipe
import com.example.data.database.model.entity.Step

data class RecipeWithSteps (
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<Step>
)