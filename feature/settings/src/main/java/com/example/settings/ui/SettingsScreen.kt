package com.example.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.settings.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = hiltViewModel()) {
    Column(Modifier.padding(horizontal = 16.dp).background(Color.White).fillMaxWidth().fillMaxHeight()) {
        val navController = rememberNavController()
        Text("Settings item 1", modifier = Modifier
            .padding(16.dp)
            .clickable { settingsViewModel.navigateBack() })
        Text("Settings item 2", modifier = Modifier
            .padding(16.dp)
            .clickable { settingsViewModel.navigateBack() })
    }
}