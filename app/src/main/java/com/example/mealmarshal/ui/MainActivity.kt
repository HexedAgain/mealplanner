package com.example.mealmarshal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.core.navigation.BottomNavigationItem
import com.example.core.navigation.ListenForNavigationEvents
import com.example.core.navigation.NavScreen
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.GeneralUISchemeLight
import com.example.core.ui.theme.LocalGeneralUITheme
import com.example.core.uinotification.UINotification
import com.example.mealmarshal.ui.theme.MealMarshalTheme
import com.example.mealmarshal.viewmodel.MainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject

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
                        MainScaffold(navigator = navigator)
                    }
                }
            }
        }
    }
}

@Composable
fun ListenForErrors(snackbarHostState: SnackbarHostState) {
    val uiNotification: UINotification = koinInject()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        uiNotification.event.collect { event ->
            var lastError: UINotification.UIError? = null
            when(event) {
                is UINotification.UIError -> {
                    lastError = event
                    val actionResId = event.actionResId
                    val actionLabel = when {
                        event.actionAsDismiss -> "DISMISS"
                        actionResId == null -> null
                        else -> context.getString(actionResId)
                    }
                    (event.scope ?: CoroutineScope(Dispatchers.Main)).launch {
                        val response: SnackbarResult = snackbarHostState.showSnackbar(
                            message = "some message: ${event.errorCode}",
                            actionLabel = actionLabel
                        )
                        if (response == SnackbarResult.Dismissed) {
                            event.onDismissed.invoke()
                        }
                        if (response == SnackbarResult.ActionPerformed) {
                            event.onAction.invoke()
                        }
                    }
                }
                // FIXME - this isn't working ... maybe I could have a method in the screen itself to listen for these errors
                //         on navigating back, that would cancel the scope and then cancel the error
                is UINotification.DismissError -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                }
                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navigator: Navigator,
    mainScreenViewModel: MainScreenViewModel = koinViewModel()
) {
    val bottomNavItems = mainScreenViewModel.bottomNavItems.toSortedSet { lhs, rhs -> lhs.navOrder.compareTo(rhs.navOrder) }
    val navScreens = mainScreenViewModel.screens
    val navController = rememberNavController()
    val uiNotification: UINotification by remember { inject(UINotification::class.java) }
    ListenForNavigationEvents(navigator = navigator, navController = navController)
    var showBottomNav by remember { mutableStateOf(true) }
    val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        uiNotification.dismissSnackbar()
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

    val snackbarHostState = remember { SnackbarHostState() }
    ListenForErrors(snackbarHostState = snackbarHostState)
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            if (showBottomNav) {
                BottomNav(bottomNavItems = bottomNavItems, navController = navController)
            }
        }
    ) { padding ->
        NavHost(
            navScreens = navScreens,
            bottomNavItems = bottomNavItems,
            navController = navController,
            padding = padding
        )
    }
}

@Composable
fun NavHost(
    padding: PaddingValues,
    navScreens: Set<NavScreen>,
    navController: NavHostController,
    bottomNavItems: Set<BottomNavigationItem>
) {

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

@Composable
fun BottomNav(
    navController: NavHostController,
    bottomNavItems: Set<BottomNavigationItem>
) {
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