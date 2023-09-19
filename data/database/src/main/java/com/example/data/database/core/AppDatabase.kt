package com.example.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.model.dao.StepDao
import com.example.data.database.model.entity.Step

@Database(entities = [Step::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun stepDao(): StepDao
}