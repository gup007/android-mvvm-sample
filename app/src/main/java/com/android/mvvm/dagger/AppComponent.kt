package com.android.mvvm.dagger

import com.android.mvvm.dagger.core.CoreComponent
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule


@ModuleScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class]
)
interface AppComponent : ModuleActivityComponent {

    @Component.Builder
    interface Builder {

        fun coreComponent(module: CoreComponent): Builder

        fun build(): AppComponent
    }
}