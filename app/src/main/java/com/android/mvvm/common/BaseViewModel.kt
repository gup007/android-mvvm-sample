package com.android.mvvm.common

import androidx.lifecycle.ViewModel
import javax.inject.Inject


open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mResourceProvider: ResourceProvider

}

