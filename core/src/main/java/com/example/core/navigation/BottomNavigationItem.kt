package com.example.core.navigation

interface BottomNavigationItem: NavigationItem {
    val navigationIconRes: Int
    val navigationIconText: Int
    val navOrder: Int
}