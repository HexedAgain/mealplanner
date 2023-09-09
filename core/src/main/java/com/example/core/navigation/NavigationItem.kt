package com.example.core.navigation

import androidx.compose.runtime.Composable

interface NavigationItem {
    val routeName: String

    @Composable
    fun Content()
}