package com.example.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.core.navigation.Navigator
import com.example.core.ui.theme.LocalGeneralUITheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ThemedAppBar(
    modifier: Modifier = Modifier,
    titleResId: Int,
    rhsActions: (@Composable RowScope.() -> Unit) = {},
    navigator: Navigator? = null
) {
    val theme = LocalGeneralUITheme.current.appBar
    SmallTopAppBar(
        actions = rhsActions,
        modifier = modifier,
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
    modifier: Modifier = Modifier,
    titleResId: Int,
    navigator: Navigator? = null,
    content: @Composable () -> Unit
) {
    val theme = LocalGeneralUITheme.current.appBar
    Column(
        modifier = theme.modifier.appBarRoot.then(modifier)
    ) {
        ThemedAppBar(titleResId = titleResId, navigator = navigator)
        content()
    }
}

@Composable
fun CollapsableThemedAppBarScreen(
    titleResId: Int,
    navigator: Navigator? = null,
    rhsActions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit
) {
    // taken from https://developer.android.com/reference/kotlin/androidx/compose/ui/input/nestedscroll/package-summary
    val toolbarHeight = 64.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }

//            override fun onPostScroll(
//                consumed: Offset,
//                available: Offset,
//                source: NestedScrollSource
//            ): Offset {
//                CoroutineScope(Dispatchers.Main).launch {
//                    delay(2000)
//                    for (x in 0..25) {
//                        delay(20)
//                        toolbarOffsetHeightPx.value = (toolbarOffsetHeightPx.value + 0.8f).coerceIn(-toolbarHeightPx, 0f)
//                    }
//                }
//                return super.onPostScroll(consumed, available, source)
//            }
        }
    }
    val theme = LocalGeneralUITheme.current.appBar
    Box(
        modifier = theme.modifier.appBarRoot
            .nestedScroll(nestedScrollConnection)
    ) {
        ThemedAppBar(
            titleResId = titleResId,
            navigator = navigator,
            rhsActions = rhsActions,
            modifier = Modifier
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .offset { IntOffset(x = 0, y = (toolbarHeightPx + toolbarOffsetHeightPx.value).roundToInt())}
        ) {
            content()
        }
    }
}