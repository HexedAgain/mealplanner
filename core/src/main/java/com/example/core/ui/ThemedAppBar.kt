package com.example.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.LocalGeneralUITheme

@Composable
fun ThemedAppBar(
    titleResId: Int,
    navigator: Navigator
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
            Icon(
                painter = painterResource(id = theme.icon.backArrow),
                contentDescription = stringResource(theme.contentDesc.backArrow),
                modifier = Modifier.clickable { navigator.navigateBack() }
            )
        },
        colors = theme.colour.appBarColours
    )
}