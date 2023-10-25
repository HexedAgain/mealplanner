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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.core.navigation.ListenForNavigationEvents
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.GeneralUISchemeLight
import com.example.core.ui.theme.LocalGeneralUITheme
import com.example.mealmarshal.ui.theme.MealMarshalTheme
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import javax.inject.Inject

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
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
//    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
    mainScreenViewModel: MainScreenViewModel = koinViewModel()
) {
    val bottomNavItems = mainScreenViewModel.bottomNavItems.toSortedSet { lhs, rhs -> lhs.navOrder.compareTo(rhs.navOrder) }
    val navScreens = mainScreenViewModel.screens
    val navController = rememberNavController()
    ListenForNavigationEvents(navigator = navigator, navController = navController)

    Scaffold(
        bottomBar = {
            // TODO to hide / show the navbar I need to get the current route as a NavigationItem
            //      and if it has showNav then compose the next bit ...
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
            modifier = Modifier.padding(padding),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(200)
                )
            },
            exitTransition = {
                fadeOut(animationSpec = tween(100))
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