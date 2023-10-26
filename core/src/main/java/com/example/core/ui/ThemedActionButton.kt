package com.example.core.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.core.ui.theme.LocalGeneralUITheme

@Composable
fun ThemedActionButton(
    onClick: () -> Unit,
    iconResId: Int,
    contentDescription: String? = null
) {
    val theme = LocalGeneralUITheme.current.appBar
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = theme.colour.actionBarActionTint
        )
    }
}