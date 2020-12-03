package com.android.mvvm.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.android.mvvm.common.BaseViewModel
import com.android.mvvm.dagger.state.AssistedSavedStateViewModelFactory
import com.android.mvvm.repository.RecipeRepo
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel @AssistedInject constructor(
    private val recipeRestRepo: RecipeRepo,
    @Assisted private val savedStateHandle: SavedStateHandle,
    @Assisted private val bundle: Bundle?
) : BaseViewModel() {

    @AssistedInject.Factory
    interface Factory : AssistedSavedStateViewModelFactory<HomeViewModel> {
        override fun create(savedStateHandle: SavedStateHandle, bundle: Bundle?): HomeViewModel
    }

    val recipeList: MutableLiveData<List<Recipe>> = MutableLiveData()

    fun fetchRecipeList(): LiveData<RecipeResponse> {
        return recipeRestRepo.getRecipeList()
    }

    fun getCurrentDate(): String {
        val date: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd MMM", Locale.getDefault())
        return df.format(date)
    }
}
