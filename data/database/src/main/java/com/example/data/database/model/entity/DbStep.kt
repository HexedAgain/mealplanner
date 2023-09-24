package com.example.data.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbStep(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val recipeId: Long,
    val body: String
)