package com.example.data.database.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["title"], unique = true)])
data class DbRecipe (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String
)