package com.android.mvvm.home

const val VIEW_TYPE_HEADER = 1
const val VIEW_TYPE_RECIPE = 2

data class Recipe(
    val recipeId: String,
    val title: String,
    var headLine: String? = null,
    var imageUrl: String? = null,
    var viewType: Int = VIEW_TYPE_RECIPE
)
