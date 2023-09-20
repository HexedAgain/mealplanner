package com.example.data.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbStep(
    @PrimaryKey val id: Int,
    val title: String,
    val recipeId: Int,
    val body: String
)