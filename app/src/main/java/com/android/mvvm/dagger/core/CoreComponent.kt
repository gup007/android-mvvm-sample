package com.android.mvvm.dagger.core

import android.content.Context
import com.android.mvvm.R
import com.android.mvvm.common.ResourceProvider
import com.android.mvvm.repository.RecipeRepo
import com.android.mvvm.repository.RecipeRepoRestImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@CoreScope
@Component(modules = [CoreModule::class])
interface CoreComponent {
    @Component.Builder
    abstract class Builder {

        @BindsInstance
        abstract fun applicationContext(context: Context): Builder

        abstract fun build(): CoreComponent
    }

    val resourceProvider: ResourceProvider
    val retrofit: Retrofit
    val recipeRepo: RecipeRepo
}

@Module
class CoreModule {

    @Provides
    @CoreScope
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @CoreScope
    internal fun provideResource(context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    @CoreScope
    fun provideRetrofit(context: Context): Retrofit {
        val retrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        val okHttpClient = OkHttpClient.Builder().build()
        return retrofitBuilder.baseUrl(context.getString(R.string.base_url))
            .client(okHttpClient).build()
    }

    @Provides
    @CoreScope
    fun provideRecipeRepo(retrofit: Retrofit): RecipeRepo {
        return RecipeRepoRestImpl(retrofit)
    }
}
