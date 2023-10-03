package com.example.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.LocalGeneralUITheme

@Composable
fun ThemedAppBar(
    titleResId: Int,
    navigator: Navigator? = null
) {
    val theme = LocalGeneralUITheme.current.appBar
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(titleResId),
                style = theme.text.titleTextStyle
            )
        },
        navigationIcon = {
            navigator?.let {
                IconButton(
                    onClick = { it.navigateUp() }
                ) {
                    Icon(
                        painter = painterResource(id = theme.icon.backArrow),
                        contentDescription = stringResource(theme.contentDesc.backArrow),
                    )
                }
            }
        },
        colors = theme.colour.appBarColours
    )
}

@Composable
fun ThemedAppBarScreen(
    titleResId: Int,
    navigator: Navigator? = null,
    content: @Composable () -> Unit
) {
    val theme = LocalGeneralUITheme.current.appBar
    Column(
        modifier = theme.modifier.appBarRoot
    ) {
        ThemedAppBar(titleResId = titleResId, navigator = navigator)
        content()
    }
}