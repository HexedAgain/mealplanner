package com.example.lab.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.core.navigation.Navigator
import com.example.core.ui.ThemedAppBarScreen
import com.example.lab.nav.AddRecipeNavScreen
import com.example.lab.ui.theme.LocalLabScreenTheme
import com.example.lab.viewmodel.AddRecipeHandler
import com.example.lab.viewmodel.AddRecipeScreenViewModel
import com.example.lab.viewmodel.KoinAddRecipeScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LabScreen(
    labViewModel: AddRecipeScreenViewModel = koinViewModel(),
    koinAddRecipeViewModel: KoinAddRecipeScreenViewModel = koinViewModel()
) {
    val theme = LocalLabScreenTheme.current
    ThemedAppBarScreen(
        titleResId = theme.text.labScreenTitle
    ) {
        koinAddRecipeViewModel.foo()
        LazyColumn(
            modifier = theme.modifier.labScreenRoot
        ) {
            item {
                AddRecipeSection(
//                    addRecipeHandler = labViewModel,
                    navigator = labViewModel
                )
            }
        }
    }
}

@Composable
fun AddRecipeSection(
//    addRecipeHandler: AddRecipeHandler,
    navigator: Navigator
) {
    val theme = LocalLabScreenTheme.current
    Button(
        onClick = {
            navigator.navigate(AddRecipeNavScreen().routeName)
        }
    ) {
        Text(text = stringResource(theme.text.addNewRecipe))
    }
}