package com.android.mvvm.common

import android.os.Bundle
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.android.mvvm.R
import com.android.mvvm.coreComponent
import com.android.mvvm.dagger.DaggerAppComponent
import com.android.mvvm.dagger.ModuleActivityComponent
import com.android.mvvm.dagger.ModuleRootActivity
import com.android.mvvm.databinding.ActivityBaseBinding


abstract class BaseActivity : ModuleRootActivity() {

    val fragmentContainerId: Int
        get() = getContainerId()

    lateinit var baseBinding: ActivityBaseBinding

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_base, null, false)
        setChildContentView(savedInstanceState, baseBinding.mainContainer, true)
        setContentView(baseBinding.root)
        supportActionBar?.hide()
        create(savedInstanceState)
    }

    override val moduleComponent: ModuleActivityComponent
        get() = DaggerAppComponent.builder().coreComponent(coreComponent()).build()

    abstract fun create(savedInstanceState: Bundle?)

    abstract fun setChildContentView(
        savedInstanceState: Bundle?,
        mainContainer: LinearLayout,
        addToParent: Boolean
    )

    open fun getContainerId(): Int {
        return -1
    }

    open fun refreshTopFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(getContainerId())
        if (currentFragment != null) {
            supportFragmentManager.beginTransaction().detach(currentFragment)
                .attach(currentFragment).commitNow()
        }
    }

    fun replaceFragment(fragment: BaseFragment<*>, addToBackStack: Boolean) {
        if (getContainerId() == -1) {
            return
        }
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(getContainerId(), fragment, fragment.javaClass.simpleName)
        if (addToBackStack) {
            ft.addToBackStack(null)
        }
        ft.commit()
    }

    private fun isDestroyingActivity(): Boolean {
        return isFinishing && isDestroyed
    }
}