package com.android.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mvvm.home.Recipe
import com.android.mvvm.home.RecipeResponse
import com.android.mvvm.repository.RecipeRepo

class RecipeRepoRestTestImpl : RecipeRepo {

    override fun getRecipeList(): LiveData<RecipeResponse> {
        val result = MutableLiveData<RecipeResponse>()
        val recipes = mutableListOf<Recipe>()
        recipes.add(Recipe("1", "First Recipe"))
        recipes.add(Recipe("2", "Second Recipe"))
        result.postValue(RecipeResponse.Success(recipes))
        return result
    }
}