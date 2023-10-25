package com.example.core.navigation.di

import com.example.core.navigation.Navigator
import com.example.core.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {
//    @Singleton
//    @Binds
//    abstract fun bindsNavigator(
//        navigatorImpl: NavigatorImpl
//    ): Navigator
}