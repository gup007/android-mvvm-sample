package com.android.mvvm.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module(includes = [CommonUiModule::class, ViewModelModule::class])
class AppModule {

    @ModuleScope
    @Provides
    internal fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }
}
