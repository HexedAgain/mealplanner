package com.example.lab.ui.theme

import com.example.assets.R

val labScreenModifier = object: LabScreenModifier {

}

val labScreenText = object: LabScreenText {
    override val addNewRecipe = R.string.add_new_recipe
    override val labScreenTitle = R.string.lab_screen_title
}

class LabScreenSchemeLight: LabScreenTheme {
    override val modifier: LabScreenModifier = labScreenModifier
    override val text: LabScreenText = labScreenText
}