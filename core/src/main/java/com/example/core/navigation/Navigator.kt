package com.example.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

interface Navigator {
    sealed class NavAction {
        object NAV_UP: NavAction()
        object NAV_BACK: NavAction()
        class  NAV_ROUTE(val route: String): NavAction()
    }
    val eventChannel: Channel<NavAction>

    fun navigateUp()

    fun navigate(route: String)

    fun navigateBack()
}

class NavigatorImpl(
    private val scope: CoroutineScope
): Navigator {
    override val eventChannel: Channel<Navigator.NavAction> = Channel()

    override fun navigateUp() {
        scope.launch {
            eventChannel.send(Navigator.NavAction.NAV_UP)
        }
    }

    override fun navigate(route: String) {
        scope.launch {
            eventChannel.send(Navigator.NavAction.NAV_ROUTE(route = route))
        }
    }

    override fun navigateBack() {
        scope.launch {
            eventChannel.send(Navigator.NavAction.NAV_BACK)
        }
    }

}