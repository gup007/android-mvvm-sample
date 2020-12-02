package com.android.mvvm

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.android.mvvm.dagger.core.CoreComponent
import com.android.mvvm.dagger.core.DaggerCoreComponent


class RecipeApp : Application() {

    private lateinit var coreComponent: CoreComponent

    override fun onCreate() {
        super.onCreate()

        coreComponent = DaggerCoreComponent.builder()
            .applicationContext(this)
            .build()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as RecipeApp).coreComponent
    }
}

fun Activity.coreComponent() = RecipeApp.coreComponent(this)