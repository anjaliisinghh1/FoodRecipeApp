package com.project.foodrecipeapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.Constants
import com.project.foodrecipeapp.databinding.FragmentHomeFoodExploreBinding
import com.project.foodrecipeapp.presentation.MealListActivity
import com.project.foodrecipeapp.presentation.adapter.ExploreAreaAdapter
import com.project.foodrecipeapp.presentation.adapter.ExploreCategoriesAdapter
import com.project.foodrecipeapp.presentation.adapter.ExploreIngredientsAdapter
import com.project.foodrecipeapp.presentation.viewModel.HomeFoodExploreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFoodExploreFragment : Fragment(R.layout.fragment_home_food_explore) {

    private lateinit var binding: FragmentHomeFoodExploreBinding
    private val viewModel: HomeFoodExploreViewModel by viewModel()
    private lateinit var exploreCategoriesAdapter: ExploreCategoriesAdapter
    private lateinit var exploreAreaAdapter: ExploreAreaAdapter
    private lateinit var exploreIngredientsAdapter: ExploreIngredientsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFoodExploreBinding.bind(view)
        setUpCategoriesRecyclerView()
        setUpAreaRecyclerView()
        setUpIngredientsRecyclerView()
        observeLiveData()
        onClickListener()
    }

    private fun setUpCategoriesRecyclerView(){
        exploreCategoriesAdapter = ExploreCategoriesAdapter()
        binding.rvExploreCategoryRecipes.apply {
            adapter = exploreCategoriesAdapter
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
        }
    }

    private fun setUpAreaRecyclerView(){
        exploreAreaAdapter = ExploreAreaAdapter()
        binding.rvExploreAreaRecipes.apply {
            adapter = exploreAreaAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }

    private fun setUpIngredientsRecyclerView(){
        exploreIngredientsAdapter = ExploreIngredientsAdapter()
        binding.rvExploreIngredientRecipes.apply {
            adapter = exploreIngredientsAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }

    private fun observeLiveData(){
        viewModel.allExploreCategoriesLiveData.observe(viewLifecycleOwner, Observer {
            exploreCategoriesAdapter.differ.submitList(it.meals.toList())
        })

        viewModel.allExploreAreaLiveData.observe(viewLifecycleOwner, Observer {
            exploreAreaAdapter.differ.submitList(it.meals.toList())
        })

        viewModel.allExploreIngredientsLiveData.observe(viewLifecycleOwner, Observer {
            exploreIngredientsAdapter.differ.submitList(it.meals.toList())
        })
    }

    private fun onClickListener(){
        exploreCategoriesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(Constants.SELECTED_VALUE,it.strCategory)
            }
            startActivity(Intent(activity, MealListActivity::class.java).putExtras(bundle))
        }

        exploreAreaAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(Constants.SELECTED_VALUE,it.strArea)
            }
            startActivity(Intent(activity, MealListActivity::class.java).putExtras(bundle))
        }

        exploreIngredientsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(Constants.SELECTED_VALUE,it.strIngredient)
            }
            startActivity(Intent(activity, MealListActivity::class.java).putExtras(bundle))
        }

    }
}