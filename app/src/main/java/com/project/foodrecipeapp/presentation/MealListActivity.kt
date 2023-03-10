package com.project.foodrecipeapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.foodrecipeapp.common.Constants
import com.project.foodrecipeapp.common.Constants.EMPTY
import com.project.foodrecipeapp.common.Constants.SELECTED_VALUE
import com.project.foodrecipeapp.databinding.ActivityMealListBinding
import com.project.foodrecipeapp.presentation.adapter.MealListAdapter
import com.project.foodrecipeapp.presentation.viewModel.MealListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MealListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealListBinding
    private val viewModel by viewModel<MealListViewModel>()
    private lateinit var mealListAdapter: MealListAdapter
    private var selectedValueForFilter = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpMealListRecyclerView()

        selectedValueForFilter = intent.getStringExtra(SELECTED_VALUE) ?: EMPTY

        if (!selectedValueForFilter.isNullOrEmpty()){
            viewModel.getRecipesByCategory(selectedValueForFilter)
        }

        observeLiveData()
        onClickListener()

    }

    private fun observeLiveData(){
        viewModel.RecipesLiveData.observe(this, Observer {

            mealListAdapter.differ.submitList(it.meals.toList())
            //mealListAdapter.setDataList(it.meals)
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
}