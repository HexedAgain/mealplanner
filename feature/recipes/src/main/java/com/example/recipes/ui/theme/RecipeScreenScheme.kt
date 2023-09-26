package com.example.recipes.ui.theme

import com.example.assets.R

val recipeScreenText = object: RecipeScreenText {
    override val recipeScreenTitle = R.string.recipes_screen_title

}

class RecipeScreenScheme: RecipeScreenTheme {
    override val text = recipeScreenText
}