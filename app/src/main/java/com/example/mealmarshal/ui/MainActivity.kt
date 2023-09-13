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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.core.navigation.Navigator
import com.example.mealmarshal.ui.theme.MealMarshalTheme
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealMarshalTheme {
                // A surface container using the 'background' color from the theme
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

@Composable
fun ListenForNavigationEvents(
    navigator: Navigator,
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    lifecycleOwner.lifecycleScope.launch {
        navigator.eventChannel.consumeAsFlow().collect { action ->
            when (action) {
                is Navigator.NavAction.NAV_ROUTE -> navController.navigate(route = action.route)
                Navigator.NavAction.NAV_UP -> navController.navigateUp()
                Navigator.NavAction.NAV_BACK -> navController.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun BottomNav(
    navigator: Navigator,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
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
        }
        ListenForNavigationEvents(navigator = navigator, navController = navController)
    }
}