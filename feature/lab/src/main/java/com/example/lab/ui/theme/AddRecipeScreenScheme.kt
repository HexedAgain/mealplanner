package com.example.lab.ui.theme

import com.example.assets.R

val addRecipeScreenText = object: AddRecipeScreenText {
    override val title = R.string.add_recipe_screen_title
}

val addRecipeScreenColour = object: AddRecipeScreenColour {

}

val addRecipeScreenIcon = object: AddRecipeScreenIcon {
    override val saveRecipe = R.drawable.check_24
}

val addRecipeScreenModifier = object: AddRecipeScreenModifier {

}

class AddRecipeScreenScheme: AddRecipeScreenTheme {
    override val text = addRecipeScreenText
    override val modifier = addRecipeScreenModifier
    override val icon = addRecipeScreenIcon
    override val colour = addRecipeScreenColour
}