package com.example.core.qualifier

import javax.inject.Qualifier

@Qualifier
annotation class IOScope

@Qualifier
annotation class MainScope

@Qualifier
annotation class DefaultScope

@Qualifier
annotation class UnconfinedScope

@Qualifier
annotation class IODispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class UnconfinedDispatcher
