package com.android.mvvm.common

import androidx.lifecycle.ViewModel
import androidx.test.espresso.IdlingResource
import com.android.mvvm.idling.SimpleIdlingResource
import javax.inject.Inject


open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mResourceProvider: ResourceProvider

    private var idlingResource: SimpleIdlingResource? = SimpleIdlingResource()

    fun getIdlingResource(): IdlingResource {
        if (idlingResource == null) {
            idlingResource = SimpleIdlingResource()
        }
        return idlingResource as SimpleIdlingResource
    }

    fun setIdleState(state: Boolean) {
        idlingResource?.setIdleState(state)
    }
}

