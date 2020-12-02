package com.android.mvvm.repository

import androidx.lifecycle.LiveData
import com.android.mvvm.home.RecipeResponse

interface RecipeRepo {

    fun getRecipeList(): LiveData<RecipeResponse>

}