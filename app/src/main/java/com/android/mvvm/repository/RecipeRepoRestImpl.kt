package com.android.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.mvvm.common.toRecipeList
import com.android.mvvm.dagger.ModuleScope
import com.android.mvvm.home.RecipeApi
import com.android.mvvm.home.RecipeEntity
import com.android.mvvm.home.RecipeResponse
import com.android.mvvm.rest.callback.APICallback
import com.android.mvvm.rest.exception.APIException
import okhttp3.Response
import retrofit2.Retrofit
import javax.inject.Inject

@ModuleScope
class RecipeRepoRestImpl @Inject constructor(
    private var retrofit: Retrofit
) : RecipeRepo {

    override fun getRecipeList(): LiveData<RecipeResponse> {
        val result = MutableLiveData<RecipeResponse>()
        retrofit.create(RecipeApi::class.java)
            .getRecipeAsync()
            .enqueue(object : APICallback<List<RecipeEntity>>() {
                override fun onSuccess(response: List<RecipeEntity>, rawResponse: Response) {
                    result.postValue(RecipeResponse.Success(response.toRecipeList()))
                }

                override fun onComplete() {

                }

                override fun onFailure(e: APIException) {
                    e.localizedMessage?.let {
                        result.postValue(RecipeResponse.Error(it))
                    }
                }
            })
        return result
    }
}