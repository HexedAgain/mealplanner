package com.example.mealmarshal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.core.navigation.Navigator
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

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