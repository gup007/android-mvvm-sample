package com.android.mvvm.common

import com.android.mvvm.home.Recipe
import com.android.mvvm.home.RecipeEntity

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(this.recipeId, this.title, this.headLine, this.imageUrl)
}

fun List<RecipeEntity>.toRecipeList(): MutableList<Recipe> {
    val recipeList = mutableListOf<Recipe>()
    for (entity in this) {
        recipeList.add(entity.toRecipe())
    }
    return recipeList
}