package com.example.core.navigation

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import kotlinx.coroutines.flow.consumeAsFlow

@Composable
fun ListenForNavigationEvents(
    navigator: Navigator,
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        navigator.eventChannel.consumeAsFlow().collect { action ->
            when (action) {
                is Navigator.NavAction.NAV_ROUTE -> {
                    navController.navigate(route = action.route)
                }
                Navigator.NavAction.NAV_UP -> {
                    navController.navigateUp()
                }
                Navigator.NavAction.NAV_BACK -> {
                    navController.popBackStack()
                }
            }
        }
    }
}