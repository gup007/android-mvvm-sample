package com.android.mvvm.dagger

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.android.mvvm.dagger.state.AssistedSavedStateViewModelFactory
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@Reusable
class CustomViewModelFactory @Inject constructor(
    private val assistedFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards AssistedSavedStateViewModelFactory<out ViewModel>>,
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) {
    /**
     * Creates instance of ViewModel either annotated with @AssistedInject or @Inject and passes dependencies it needs.
     */
    fun create(owner: SavedStateRegistryOwner, defaultArgs: Bundle? = null) =
        object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val viewModel =
                    createAssistedInjectViewModel(modelClass, handle, defaultArgs)
                        ?: createInjectViewModel(modelClass)
                        ?: throw IllegalArgumentException("Unknown model class $modelClass")

                return viewModel as T
            }
        }

    /**
     * Creates ViewModel based on @AssistedInject constructor and its factory
     */
    private fun <T : ViewModel?> createAssistedInjectViewModel(
        modelClass: Class<T>,
        handle: SavedStateHandle,
        defaultArgs: Bundle?
    ): ViewModel? {
        val creator = assistedFactories[modelClass]
            ?: assistedFactories.asIterable()
                .firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: return null

        return creator.create(handle, defaultArgs)
    }

    /**
     * Creates ViewModel based on regular Dagger @Inject constructor
     */
    private fun <T : ViewModel?> createInjectViewModel(modelClass: Class<T>): ViewModel? {
        val creator = viewModelProviders[modelClass]
            ?: viewModelProviders.asIterable()
                .firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: return null

        return creator.get()
    }
}