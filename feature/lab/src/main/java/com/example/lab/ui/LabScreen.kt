package com.example.lab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.ui.ThemedAppBar
import com.example.core.ui.ThemedAppBarScreen
import com.example.lab.ui.theme.LocalLabScreenTheme
import com.example.lab.viewmodel.LabScreenViewModel

@Composable
fun LabScreen(
    labViewModel: LabScreenViewModel = hiltViewModel()
) {
    val theme = LocalLabScreenTheme.current
    ThemedAppBarScreen(
        titleResId = theme.text.labScreenTitle,
        navigator = labViewModel
    ) {
        LazyColumn(
            modifier = theme.modifier.labScreenRoot
        ) {
            item {
                AddRecipeSection()
            }
        }
    }
}

@Composable
fun AddRecipeSection() {
    val theme = LocalLabScreenTheme.current
    Button(
        onClick = {}
    ) {
        Text(text = stringResource(theme.text.addNewRecipe))
    }
}