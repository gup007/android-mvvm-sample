package com.android.mvvm

import android.os.Bundle
import com.android.mvvm.common.VMBaseActivity
import com.android.mvvm.databinding.ActivityHomeBinding
import com.android.mvvm.home.HomeFragment
import com.android.mvvm.home.HomeViewModel

class HomeActivity : VMBaseActivity<HomeViewModel, ActivityHomeBinding>() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getContainerId(): Int {
        return binding.trandingRepoContainer.id
    }

    override fun onCreate(
        instance: Bundle?,
        viewModel: HomeViewModel,
        binding: ActivityHomeBinding
    ) {
        this.viewModel = viewModel
        this.binding = binding
        replaceFragment(HomeFragment(), false)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_home
    }

}
