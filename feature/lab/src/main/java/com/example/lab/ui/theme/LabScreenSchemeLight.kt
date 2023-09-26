package com.example.lab.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.assets.R
import com.example.assets.theme.Metrics

val labScreenModifier = object: LabScreenModifier {
    override val labScreenRoot = Modifier
        .padding(Metrics.paddingNormal)
}

val labScreenText = object: LabScreenText {
    override val addNewRecipe = R.string.add_new_recipe
    override val labScreenTitle = R.string.lab_screen_title
}

class LabScreenSchemeLight: LabScreenTheme {
    override val modifier: LabScreenModifier = labScreenModifier
    override val text: LabScreenText = labScreenText
}