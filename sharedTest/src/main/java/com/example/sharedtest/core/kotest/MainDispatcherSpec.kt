package com.example.sharedtest.core.kotest

import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FreeSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
abstract class MainDispatcherSpec(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): FreeSpec() {

    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        Dispatchers.setMain(testDispatcher)
    }

    override fun afterSpec(f: suspend (Spec) -> Unit) {
        super.afterSpec(f)
        Dispatchers.resetMain()
    }
}