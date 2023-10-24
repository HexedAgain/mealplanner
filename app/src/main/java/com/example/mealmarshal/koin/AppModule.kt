package com.example.mealmarshal.koin

import com.example.core.koin.coreModule
import com.example.lab.koin.labModule
import com.example.mealmarshal.viewmodel.MainKoinViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val appModule = module {
    includes(labModule, coreModule)
//    factory {
//        named("MainDispatcher")
//        Dispatchers.Main
//    }
    viewModel {
        val ioDispatcher: CoroutineDispatcher by inject(named("IODispatcher"))
        MainKoinViewModel(
            ioDispatcher
//            ioDispatcher = get(qualifier = StringQualifier("IODispatcher"))
//            ioDispatcher = get(named("IODispatcher"))
        )
    }
}