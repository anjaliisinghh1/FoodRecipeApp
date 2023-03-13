package com.project.foodrecipeapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.Constants
import com.project.foodrecipeapp.common.Resource
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
        viewModel.allExploreCategoriesLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { mealRecipes ->
                        exploreCategoriesAdapter.differ.submitList(mealRecipes.meals.toList())

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,"An Error Occured $message", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.allExploreAreaLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { mealRecipes ->
                        exploreAreaAdapter.differ.submitList(mealRecipes.meals.toList())

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,"An Error Occured $message", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        viewModel.allExploreIngredientsLiveData.observe(viewLifecycleOwner, Observer {  response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { mealRecipes ->
                        exploreIngredientsAdapter.differ.submitList(mealRecipes.meals.toList())

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity,"An Error Occured $message", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
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
                putString(Constants.SELECTED_VALUE_AREA,it.strArea)
            }
            startActivity(Intent(activity, MealListActivity::class.java).putExtras(bundle))
        }

        exploreIngredientsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(Constants.SELECTED_VALUE_INGREDIENTS,it.strIngredient)
            }
            startActivity(Intent(activity, MealListActivity::class.java).putExtras(bundle))
        }
    }

    private fun hideProgressBar(){
        binding.exploreListProgressBar.visibility = View.GONE
        binding.tvExploreCategory.visibility = View.VISIBLE
        binding.tvExploreArea.visibility = View.VISIBLE
        binding.tvExploreIngredient.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        binding.exploreListProgressBar.visibility = View.VISIBLE
        binding.tvExploreCategory.visibility = View.GONE
        binding.tvExploreArea.visibility = View.GONE
        binding.tvExploreIngredient.visibility = View.GONE
    }
}