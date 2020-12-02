package com.android.mvvm.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mvvm.R
import com.android.mvvm.common.BaseFragment
import com.android.mvvm.common.RecyclerItemClickListener
import com.android.mvvm.common.AdapterSpaceDecoration
import com.android.mvvm.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(), RecyclerItemClickListener<Recipe> {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var layoutManager: LinearLayoutManager

    override fun getLayoutResId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateView(instance: Bundle?, binding: FragmentHomeBinding) {
        val storeViewModel = getActivityViewModel(HomeViewModel::class.java)
        binding.viewModel = storeViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        this.binding = binding
        viewModel = binding.viewModel!!
        initRecipeListView()
        requestData()
    }

    private fun requestData() {
        binding.progressCircular.visibility = View.VISIBLE
        viewModel.fetchRecipeList().observe(viewLifecycleOwner, Observer {
            when (it) {
                is RecipeResponse.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    it.recipeList.add(
                        0, Recipe(
                            "", viewModel.getCurrentDate(), viewType = VIEW_TYPE_HEADER
                        )
                    )
                    viewModel.recipeList.postValue(it.recipeList)
                }
                is RecipeResponse.Error -> {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initRecipeListView() {
        layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.rvRecipeList.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(
            context,
            layoutManager.orientation
        )
        val dimension: Float? = context?.resources?.getDimension(R.dimen.dimen_24dp)
        dimension?.let {
            binding.rvRecipeList.addItemDecoration(AdapterSpaceDecoration(dimension.toInt()))
        }
        binding.rvRecipeList.addItemDecoration(dividerItemDecoration)
        binding.rvRecipeList.adapter = HomeListAdapter(binding.rvRecipeList, this)
    }

    override fun onItemClick(view: View, item: Recipe) {
        Toast.makeText(context, "Clicked on ${item.title}", Toast.LENGTH_SHORT).show()
    }
}