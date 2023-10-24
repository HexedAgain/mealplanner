package com.example.data.database.koin

import com.example.data.database.core.AppDatabase
import com.example.data.database.model.dao.StepDao
import org.koin.dsl.module

//val dataDatabaseModule = module {
//    single {
//        val database = get<AppDatabase>()
//        database.stepDao()
//    }
//
//    single {
//        val database = get<AppDatabase>()
//        database.recipeDao()
//    }
//}