package com.android.mvvm.dagger


import androidx.lifecycle.ViewModel
import com.android.mvvm.dagger.state.AssistedSavedStateViewModelFactory
import com.android.mvvm.home.HomeViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes = [AssistedInject_ViewModelModule::class])
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsSomeViewModel(f: HomeViewModel.Factory): AssistedSavedStateViewModelFactory<out ViewModel>

}
