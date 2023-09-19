package com.example.data.database.model.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.database.model.entity.Step

@Dao
interface StepDao {
    @Query("SELECT * FROM step WHERE id = :id")
    fun findById(id: String): Step?
}