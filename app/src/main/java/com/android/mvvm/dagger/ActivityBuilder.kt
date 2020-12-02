package com.android.mvvm.dagger

import com.android.mvvm.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [ActivityModule::class, ActivityFragmentBuilder::class])
    internal abstract fun bindHomeActivity(): HomeActivity

}
