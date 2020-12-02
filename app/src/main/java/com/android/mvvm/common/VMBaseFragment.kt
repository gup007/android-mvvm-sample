package com.android.mvvm.common

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class VMBaseFragment<M : ViewModel, B : ViewDataBinding> : BaseFragment<B>() {

    override fun onCreateView(instance: Bundle?, binding: B) {
        val viewModel = ViewModelProvider(this).get(getViewModel())
        onCreate(instance, viewModel, binding)
    }

    protected abstract fun getViewModel(): Class<M>

    protected abstract fun onCreate(instance: Bundle?, viewModel: M, binding: B)

    override fun getTitle(): CharSequence? {
        return null
    }
}