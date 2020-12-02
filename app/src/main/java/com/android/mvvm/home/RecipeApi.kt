package com.android.mvvm.home

import retrofit2.Call
import retrofit2.http.GET

interface RecipeApi {

    @GET("/android-test/recipes.json")
    fun getRecipeAsync(): Call<List<RecipeEntity>>

}