package com.example.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
//class SettingsViewModel @Inject constructor(
class SettingsViewModel(
   private val navigator: Navigator
):
    Navigator by navigator,
    ViewModel() {
}