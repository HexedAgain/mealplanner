package com.example.mealmarshal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val bottomNavItems: MutableSet<BottomNavigationItem>
):
    ViewModel() {
}