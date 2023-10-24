package com.example.mealmarshal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.qualifier.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher

class MainKoinViewModel(
    private val ioDispatcher: CoroutineDispatcher,
): ViewModel() {
    init {
        val a = 5
        if (a.equals(4)) {
            val b = 3
        }
    }

    fun foo() {

    }
}