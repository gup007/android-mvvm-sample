package com.android.mvvm.dagger

import androidx.databinding.ViewDataBinding
import com.android.mvvm.common.BaseFragment
import com.android.mvvm.common.VMBaseFragment
import dagger.Binds
import dagger.Module

@Module
abstract class FragmentBuilderModule {

    @ModuleScope
    @Binds
    internal abstract fun bindBaseActivity(fragment: BaseFragment<ViewDataBinding>): BaseFragment<ViewDataBinding>

    @ModuleScope
    @Binds
    internal abstract fun bindVMBaseActivity(fragment: VMBaseFragment<*, *>): VMBaseFragment<*, *>


    @ModuleScope
    @Binds
    internal abstract fun bindContentFragment(fragment: VMBaseFragment<*, *>): VMBaseFragment<*, *>

}
