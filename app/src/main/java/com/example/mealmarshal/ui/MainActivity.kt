package com.example.mealmarshal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.BottomNavigationItem
import com.example.mealmarshal.ui.theme.MealMarshalTheme
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import com.example.recipes.nav.RecipesNavScreen
import com.example.settings.nav.SettingsNavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealMarshalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BottomNav()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BottomNav(mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    val bottomNavItems = mainScreenViewModel.bottomNavItems.toSortedSet { lhs, rhs -> lhs.navOrder.compareTo(rhs.navOrder) }
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar {
                val navstackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navstackEntry?.destination
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.routeName } == true,
                        onClick = {
                            navController.navigate(item.routeName) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.navigationIconRes),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(id = item.navigationIconText)
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = bottomNavItems.first().routeName,
            modifier = Modifier.padding(padding)
        ) {
            bottomNavItems.forEach { navItem ->
                composable(navItem.routeName) {
                    navItem.Content()
                }
            }
        }
    }
}