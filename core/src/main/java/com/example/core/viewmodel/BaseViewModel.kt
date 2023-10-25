package com.example.core.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.ui.UIState
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S: UIState>: ViewModel() {
    // this viewmodel needs to be holding onto all app viewstate (perhaps)
    abstract val state: StateFlow<S>
}