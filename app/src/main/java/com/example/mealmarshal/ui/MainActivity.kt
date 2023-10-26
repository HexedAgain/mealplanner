package com.example.mealmarshal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.core.navigation.ListenForNavigationEvents
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.GeneralUISchemeLight
import com.example.core.ui.theme.LocalGeneralUITheme
import com.example.mealmarshal.ui.theme.MealMarshalTheme
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    val navigator by inject<Navigator>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealMarshalTheme {
                // A surface container using the 'background' color from the theme
                CompositionLocalProvider(LocalGeneralUITheme provides GeneralUISchemeLight()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        BottomNav(navigator = navigator)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BottomNav(
    navigator: Navigator,
    mainScreenViewModel: MainScreenViewModel = koinViewModel()
) {
    val bottomNavItems = mainScreenViewModel.bottomNavItems.toSortedSet { lhs, rhs -> lhs.navOrder.compareTo(rhs.navOrder) }
    val navScreens = mainScreenViewModel.screens
    val navController = rememberNavController()
    ListenForNavigationEvents(navigator = navigator, navController = navController)
    var showBottomNav by remember { mutableStateOf(true) }
    val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (bottomNavItems.map { it.routeName }.contains(destination.route)) {
            showBottomNav = true
        } else {
            navScreens.find { it.routeName == destination.route }?.let {
                showBottomNav = it.showNavBar
            }
        }
    }
    DisposableEffect(Unit) {
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
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
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = bottomNavItems.first().routeName,
            modifier = Modifier.padding(padding),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            }
        ) {
            bottomNavItems.forEach { navItem ->
                if (navItem.isDialog) {
                    dialog(
                        route = navItem.routeName,
                        dialogProperties = DialogProperties()) {
                        navItem.Content()
                    }
                } else {
                    composable(navItem.routeName) {
                        navItem.Content()
                    }
                }
            }
            navScreens.forEach { screen ->
                composable(screen.routeName) {
                    screen.Content()
                }
            }
        }
    }
}