package com.example.sharedtest.core.kotest

import com.example.core.koin.coreModule
import com.example.core.navigation.Navigator
import com.example.data.database.model.dao.RecipeDao
import com.example.lab.koin.labModule
import com.example.sharedtest.core.navigation.MockNavigator
import com.example.sharedtest.data.database.model.dao.MockRecipeDao
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest

@OptIn(ExperimentalCoroutinesApi::class)
abstract class KoinMainDispatcherSpec(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): KoinTest, FreeSpec() {
    lateinit var mockRecipeDao: MockRecipeDao
    lateinit var mockNavigator: MockNavigator

    val mockModule = module() {
        factory() {
            mockRecipeDao = MockRecipeDao()
            mockRecipeDao as RecipeDao
        }
        single {
            mockNavigator = MockNavigator()
            MockNavigator() as Navigator
        }
        single(named("IODispatcher")) {
            testDispatcher as CoroutineDispatcher
        }
    }

    override suspend fun beforeSpec(spec: Spec) {
        super.beforeSpec(spec)
        Dispatchers.setMain(testDispatcher)
    }

    override suspend fun beforeAny(testCase: TestCase) {
        super.beforeAny(testCase)
        koinApplication {
            stopKoin() // in case of nested tests
            startKoin {
                allowOverride(override = true)
                modules(labModule, coreModule, mockModule)
            }
        }
    }

    override fun afterSpec(f: suspend (Spec) -> Unit) {
        super.afterSpec(f)
        Dispatchers.resetMain()
    }

    override suspend fun afterAny(testCase: TestCase, result: TestResult) {
        super.afterAny(testCase, result)
        stopKoin()
    }
}