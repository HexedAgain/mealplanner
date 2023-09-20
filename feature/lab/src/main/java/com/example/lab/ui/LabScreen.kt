package com.example.lab.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LabScreen() {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Box(
            modifier = Modifier.height(100.dp).fillMaxWidth().background(Color.Yellow)
        ) {
            Text("Lab")
        }
    }
}