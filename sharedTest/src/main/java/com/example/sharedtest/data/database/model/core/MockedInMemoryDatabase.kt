package com.example.sharedtest.data.database.model.core

import android.content.Context
import androidx.room.Room
import com.example.data.database.core.AppDatabase
import io.mockk.every
import io.mockk.mockk
import java.io.Closeable
import java.io.File

// FIXME - don't see any way of getting this to work in unit tests.
//         if dao tests are required this will need to be done in robolectric
class MockedInMemoryDatabase: Closeable {
    val mockContext = mockk<Context>()
    val cacheDir = getPathHere()

    fun createDb(): AppDatabase {
        every { mockContext.cacheDir } returns File(cacheDir)
        return Room.inMemoryDatabaseBuilder(mockContext, AppDatabase::class.java).build()
    }

    override fun close() {
        File(cacheDir).delete()
    }

    fun getPathHere(): String {
        val thisFilename = File("MockedInMemoryDatabase.kt").absolutePath
        val filenameParts = thisFilename.split(File.separator)
        return filenameParts.subList(0, filenameParts.size - 1).joinToString(File.separator)
    }
}