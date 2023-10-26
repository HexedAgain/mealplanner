package com.example.core.koin

import com.example.core.uinotification.UINotification
import com.example.core.uinotification.UINotificationImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    factory(named("IODispatcher")) { Dispatchers.IO }
    factory(named("MainDispatcher")) { Dispatchers.Main }
    factory(named("DefaultDispatcher")) { Dispatchers.Default }
    factory(named("UnconfinedDispatcher")) { Dispatchers.Unconfined }
    factory(named("IOScope")) { CoroutineScope(Dispatchers.IO) }
    factory(named("MainScope")) { CoroutineScope(Dispatchers.Main) }
    factory(named("DefaultScope")) { CoroutineScope(Dispatchers.Default) }
    factory(named("UnconfinedScope")) { CoroutineScope(Dispatchers.Unconfined) }
    single {
        UINotificationImpl() as UINotification
    }
}