package com.example.data.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.database.model.entity.DbRecipe
import com.example.data.database.model.entity.DbStep

data class DbRecipeWithSteps (
    @Embedded val recipe: DbRecipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<DbStep>
)