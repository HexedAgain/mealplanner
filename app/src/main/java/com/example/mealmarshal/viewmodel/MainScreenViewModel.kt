package com.example.mealmarshal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.NavScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
//class MainScreenViewModel @Inject constructor(
class MainScreenViewModel(
    val bottomNavItems: MutableSet<BottomNavigationItem>,
    val screens: MutableSet<NavScreen>
):
    ViewModel() {
}