package com.example.mealmarshal

import android.app.Application
import com.example.core.koin.coreModule
import com.example.lab.koin.labModule
import com.example.mealmarshal.koin.appModule
import dagger.hilt.android.HiltAndroidApp
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

@HiltAndroidApp
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}