package com.android.mvvm.home

import com.google.gson.annotations.SerializedName

data class RecipeEntity(
    @SerializedName("id")
    val recipeId: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("headline")
    var headLine: String? = null,
    @SerializedName("image")
    var imageUrl: String? = null
)