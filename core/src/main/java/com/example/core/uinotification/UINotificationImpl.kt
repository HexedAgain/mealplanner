package com.example.core.uinotification

import com.example.core.error.ErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UINotificationImpl: UINotification {
    private val _error = MutableStateFlow<UINotification.UIError?>(null)
    override val error = _error.asStateFlow()

    override fun postError(
        errorCode: ErrorCode,
        actionResId: Int?,
        onAction: () -> Unit,
        onDismissed: () -> Unit
    ) {
        _error.value = UINotification.UIError(
            errorCode = errorCode,
            actionResId = actionResId,
            onAction = onAction,
            onDismissed = onDismissed
        )
    }

    override fun postDismissableError(
        errorCode: ErrorCode,
        onDismissed: () -> Unit
    ) {
        _error.value = UINotification.UIError(
            actionAsDismiss = true,
            errorCode = errorCode,
            onAction = onDismissed,
            onDismissed = onDismissed
        )
    }
}