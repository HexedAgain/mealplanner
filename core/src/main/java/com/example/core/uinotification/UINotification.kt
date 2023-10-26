package com.example.core.uinotification

import com.example.core.error.ErrorCode
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface UINotification {
    interface UIEvent
    data class UIError(
        val actionAsDismiss: Boolean = false,
        val errorCode: ErrorCode?,
        val actionResId: Int? = null,
        val onAction: () -> Unit = {},
        val onDismissed: () -> Unit = {}
    ): UIEvent
    data class DismissError(
        val uniqueId: String = UUID.randomUUID().toString()
    ): UIEvent

    val event: StateFlow<UIEvent?>

    fun postError(
        errorCode: ErrorCode,
        actionResId: Int? = null,
        onAction: () -> Unit = {},
        onDismissed: () -> Unit = {}
    )

    fun postDismissableError(
        errorCode: ErrorCode,
        onDismissed: () -> Unit
    )

    fun dismissSnackbar()
}