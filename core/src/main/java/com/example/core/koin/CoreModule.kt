package com.example.core.koin

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    factory(named("IODispatcher")) {
        Dispatchers.IO
    }
    factory(named("MainDispatcher")) {
        Dispatchers.Main
    }
}