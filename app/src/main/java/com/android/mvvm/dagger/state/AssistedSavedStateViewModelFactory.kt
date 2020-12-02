package com.android.mvvm.dagger.state

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface AssistedSavedStateViewModelFactory<T : ViewModel> {
   fun create(savedStateHandle: SavedStateHandle, bundle: Bundle?): T
}