package com.example.core.ui

import com.example.core.ui.ViewEvent

interface ViewEventHandler {
    fun postEvent(event: ViewEvent)
}