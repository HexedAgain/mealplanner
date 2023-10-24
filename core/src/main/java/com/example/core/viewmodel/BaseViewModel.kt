package com.example.core.viewmodel

import androidx.lifecycle.ViewModel

data class AppViewState(val foo: String)

class BaseViewModel: ViewModel() {
    // this viewmodel needs to be holding onto all app viewstate (perhaps)
    val state: AppViewState = AppViewState("")
}