package com.example.core.uinotification

import com.example.core.error.ErrorCode
import kotlinx.coroutines.flow.StateFlow

interface UINotification {
    data class UIError(
        val actionAsDismiss: Boolean = false,
        val errorCode: ErrorCode?,
        val actionResId: Int? = null,
        val onAction: () -> Unit = {},
        val onDismissed: () -> Unit = {}
    )
    val error: StateFlow<UIError?>

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
}