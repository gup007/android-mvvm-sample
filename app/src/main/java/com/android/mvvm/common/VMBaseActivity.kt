package com.android.mvvm.common

import android.os.Bundle
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.android.mvvm.dagger.CustomViewModelFactory
import javax.inject.Inject

abstract class VMBaseActivity<M : BaseViewModel, B : ViewDataBinding> : BaseActivity() {

    @Inject
    lateinit var abstractViewModelFactory: CustomViewModelFactory

    protected abstract fun getViewModel(): Class<M>

    protected abstract fun onCreate(instance: Bundle?, viewModel: M, binding: B)

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    private lateinit var binding: B
    override fun setChildContentView(
        savedInstanceState: Bundle?,
        mainContainer: LinearLayout,
        addToParent: Boolean
    ) {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            getLayoutResId(),
            mainContainer,
            addToParent
        )
    }

    final override fun create(savedInstanceState: Bundle?) {
//        val viewModel = ViewModelProvider(this, SavedStateViewModelFactory(application, this, Bundle()))[getViewModel()]
        val factory = abstractViewModelFactory.create(this)
        val viewModel = ViewModelProvider(this, factory).get(getViewModel())
        onCreate(savedInstanceState, viewModel, binding)
    }
}