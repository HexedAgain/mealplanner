package com.example.core.uinotification

import com.example.core.error.ErrorCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UINotificationImpl: UINotification {
    private val _event = MutableStateFlow<UINotification.UIEvent?>(null)
    override val event = _event.asStateFlow()

    override fun postError(
        errorCode: ErrorCode,
        actionResId: Int?,
        onAction: () -> Unit,
        onDismissed: () -> Unit
    ) {
        _event.value = UINotification.UIError(
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
        _event.value = UINotification.UIError(
            actionAsDismiss = true,
            errorCode = errorCode,
            onAction = onDismissed,
            onDismissed = onDismissed
        )
    }

    override fun dismissSnackbar() {
        _event.value = UINotification.DismissSnackbar()
    }
}