package com.example.sharedtest.core.navigation

import com.example.core.navigation.Navigator
import kotlinx.coroutines.channels.Channel

class MockNavigator: Navigator {
    override val eventChannel: Channel<Navigator.NavAction> = Channel(Channel.UNLIMITED)

    var didNavigateUp = false
    override fun navigateUp() {
        didNavigateUp = true
    }

    var didNavigate = false
    var routeSupplied: String? = null
    override fun navigate(route: String) {
        didNavigate = true
        routeSupplied = route
    }

    var didNavigateBack = false
    override fun navigateBack() {
        didNavigateBack = true
    }
}