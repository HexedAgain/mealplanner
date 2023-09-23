package com.example.data.repository

//import org.junit.Test
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
//import org.junit.jupiter.api.Test

//import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
////        assertEquals(4, 2 + 3)
//        true shouldBe false
//    }
//}

class Kotest: FreeSpec() {
    init {
        "some-test" {
            true shouldBe false
        }
    }
}