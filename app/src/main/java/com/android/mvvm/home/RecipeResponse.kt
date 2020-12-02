package com.android.mvvm.home

sealed class RecipeResponse {

    internal data class Success(val recipeList: MutableList<Recipe>) : RecipeResponse()

    internal data class Error(val error: String) : RecipeResponse()
}