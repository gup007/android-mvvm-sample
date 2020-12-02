package com.android.mvvm.common

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mvvm.dagger.CustomViewModelFactory
import com.android.mvvm.dagger.ModuleChildFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : ModuleChildFragment() {

    @Inject
    lateinit var mResourceProvider: ResourceProvider

    @Inject
    lateinit var abstractFactory: CustomViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: B = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        onCreateView(savedInstanceState, binding)
        if (activity != null) {
            if (activity is AppCompatActivity) {
                if ((activity as AppCompatActivity).supportActionBar != null && !TextUtils.isEmpty(
                        getTitle()
                    )
                ) {
                    (activity as AppCompatActivity).supportActionBar?.title = getTitle()
                }
            }
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    open fun getTitle(): CharSequence? {
        return null
    }

    protected abstract fun onCreateView(instance: Bundle?, binding: B)

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    protected fun <T : ViewModel> getActivityViewModel(vClass: Class<T>): T? {
        if (activity == null) {
            return null
        }
        val factory = abstractFactory.create(this)
        return ViewModelProvider(activity!!, factory).get(vClass)
    }


    protected fun <T : BaseViewModel> getFragmentViewModel(vClass: Class<T>): T? {
        if (activity == null) {
            return null
        }
        val factory = abstractFactory.create(this, arguments)
        return ViewModelProvider(this, factory).get(vClass)
    }

    open fun onBackPress(): Boolean {
        return false
    }

    fun handleChildOnBackPress(): Boolean {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack()
            return true
        }
        return false
    }
}
