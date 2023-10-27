package com.example.mealmarshal.ui.errorstrings

import android.content.Context
import com.example.core.error.ErrorCode
import com.example.assets.R
import com.example.core.error.UIRecipeErrorCode
import com.example.mealmarshal.BuildConfig

object ErrorMapping {
    val mapping: HashMap<ErrorCode, Int> = hashMapOf(
        UIRecipeErrorCode.SAVE_RECIPE_DUPLICATE_STEP to R.string.error_generic,
        UIRecipeErrorCode.SAVE_RECIPE_NO_STEPS to R.string.error_save_recipe_no_steps,
        UIRecipeErrorCode.SAVE_RECIPE_NO_RECIPE to R.string.error_save_recipe_no_recipe,
        UIRecipeErrorCode.SAVE_RECIPE_INCOMPLETE_STEP to R.string.error_save_recipe_incomplete_step,
    )

    // Will need args I guess
    fun getErrorString(context: Context, errorCode: ErrorCode): String {
        val genericError = R.string.error_generic
        val resId = mapping.getOrDefault(errorCode, genericError)
        return if (resId == genericError && BuildConfig.DEBUG) {
            context.getString(resId, errorCode.toString())
        } else {
            context.getString(resId)
        }
    }
}