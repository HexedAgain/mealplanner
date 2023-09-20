package com.example.data.database.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.database.model.entity.DbStep

@Dao
interface StepDao {
    @Query("SELECT * FROM DbStep WHERE id = :id")
    fun findById(id: String): DbStep?

    @Insert
    fun insertStep(step: DbStep)

    @Update
    fun updateStep(updatedDbStep: DbStep)

}