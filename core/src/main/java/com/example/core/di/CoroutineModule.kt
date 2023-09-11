package com.example.core.di

import com.example.core.qualifier.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @UnconfinedDispatcher
    @Provides
    fun providesUnconfinedDispatcher(): CoroutineDispatcher {
        return Dispatchers.Unconfined
    }

    @DefaultScope
    @Provides
    fun providesDefaultScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Default)
    }

    @IOScope
    @Provides
    fun providesIOScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @MainScope
    @Provides
    fun providesMainScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Main)
    }

    @UnconfinedScope
    @Provides
    fun providesUnconfinedScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Unconfined)
    }
}