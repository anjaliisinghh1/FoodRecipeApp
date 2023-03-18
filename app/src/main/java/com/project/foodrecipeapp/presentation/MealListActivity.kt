package com.project.foodrecipeapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.foodrecipeapp.common.Constants
import com.project.foodrecipeapp.common.Constants.EMPTY
import com.project.foodrecipeapp.common.Constants.SEARCH_TEXT
import com.project.foodrecipeapp.common.Constants.SELECTED_VALUE
import com.project.foodrecipeapp.common.Constants.SELECTED_VALUE_AREA
import com.project.foodrecipeapp.common.Constants.SELECTED_VALUE_INGREDIENTS
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.databinding.ActivityMealListBinding
import com.project.foodrecipeapp.presentation.adapter.MealListAdapter
import com.project.foodrecipeapp.presentation.viewModel.MealListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealListBinding
    private val viewModel by viewModel<MealListViewModel>()
    private lateinit var mealListAdapter: MealListAdapter
    private var selectedValueForFilter = EMPTY
    private var selectedValueForArea = EMPTY
    private var selectedValueForIngredient = EMPTY
    private var searchText = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpMealListRecyclerView()

        selectedValueForFilter = intent.getStringExtra(SELECTED_VALUE) ?: EMPTY
        selectedValueForArea = intent.getStringExtra(SELECTED_VALUE_AREA) ?: EMPTY
        selectedValueForIngredient = intent.getStringExtra(SELECTED_VALUE_INGREDIENTS) ?: EMPTY
        searchText = intent.getStringExtra(SEARCH_TEXT) ?: EMPTY

        if (!selectedValueForFilter.isNullOrEmpty()){
            viewModel.getRecipesByCategory(selectedValueForFilter)
        }

        if (!selectedValueForArea.isNullOrEmpty()){
            viewModel.getRecipesByArea(selectedValueForArea)
        }

        if (!selectedValueForIngredient.isNullOrEmpty()){
            viewModel.getRecipesByIngredients(selectedValueForIngredient)
        }

        if (searchText.isNotEmpty()){
            viewModel.getSearchedMeals(searchText)
        }
        observeLiveData()
        onClickListener()

    }

    private fun observeLiveData(){
        viewModel.recipesLiveData.observe(this, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { mealRecipes ->
                        mealListAdapter.differ.submitList(mealRecipes.meals.toList())

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(this,"An Error Occured $message", Toast.LENGTH_LONG).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun onClickListener(){
        mealListAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putString(Constants.MEAL_NAME,it.strMeal)
                putString(Constants.MEAL_IMG_URL,it.strMealThumb)
                putString(Constants.MEAL_ID,it.idMeal)
            }
            startActivity(Intent(applicationContext,MealDetailActivity::class.java).putExtras(bundle))
        }
    }

    private fun setUpMealListRecyclerView(){
        mealListAdapter = MealListAdapter()
        binding.rvRecipes.apply {
            adapter = mealListAdapter
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
        }
    }

    private fun hideProgressBar(){
        binding.mealsListProgressBar.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.mealsListProgressBar.visibility = View.VISIBLE
    }
}