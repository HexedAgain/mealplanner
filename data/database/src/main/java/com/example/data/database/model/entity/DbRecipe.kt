package com.example.data.database.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbRecipe (
    @PrimaryKey val id: Int,
    val title: String
)