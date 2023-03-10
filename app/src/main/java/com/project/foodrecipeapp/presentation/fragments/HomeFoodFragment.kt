package com.project.foodrecipeapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.Constants.MEAL_ID
import com.project.foodrecipeapp.common.Constants.MEAL_IMG_URL
import com.project.foodrecipeapp.common.Constants.MEAL_NAME
import com.project.foodrecipeapp.common.Constants.SELECTED_VALUE
import com.project.foodrecipeapp.common.loadImage
import com.project.foodrecipeapp.databinding.FragmentHomeFoodBinding
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.presentation.MealDetailActivity
import com.project.foodrecipeapp.presentation.MealListActivity
import com.project.foodrecipeapp.presentation.adapter.CategoriesAdapter
import com.project.foodrecipeapp.presentation.adapter.TrendingRecipesAdapter
import com.project.foodrecipeapp.presentation.viewModel.HomeFoodViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFoodFragment : Fragment(R.layout.fragment_home_food){

    private lateinit var binding: FragmentHomeFoodBinding
    private val viewModel: HomeFoodViewModel by viewModel()
    private lateinit var meal : MealsList
    private lateinit var trendingRecipesAdapter: TrendingRecipesAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFoodBinding.bind(view)
        setUpTrendingRecipesRecyclerView()
        setUpCategoryAdapter()
        observeLiveData()
        onClickListener()
    }

    private fun observeLiveData(){
        viewModel.randomMealLiveData.observe(viewLifecycleOwner, Observer {
            meal = it
            val randomMeal = it.meals[0]
            if (!randomMeal.strMealThumb.isNullOrEmpty()){
                loadImage(binding.ivRandomFood,randomMeal.strMealThumb)
            }
        })

        viewModel.trendingRecipesLiveData.observe(viewLifecycleOwner, Observer {
            trendingRecipesAdapter.setData(it.meals)
        })

        viewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setData(it.categories)
        })
    }

    private fun onClickListener(){
        binding.randomImgCardView.setOnClickListener {
            val mealData = meal.meals[0]
            val bundle = Bundle().apply {
                putString(MEAL_NAME,mealData.strMeal)
                putString(MEAL_IMG_URL,mealData.strMealThumb)
                putString(MEAL_ID,mealData.idMeal)
            }
            startActivity(Intent(activity,MealDetailActivity::class.java).putExtras(bundle))
        }

        trendingRecipesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(MEAL_NAME,it.strMeal)
                putString(MEAL_IMG_URL,it.strMealThumb)
                putString(MEAL_ID,it.idMeal)
            }
            startActivity(Intent(activity,MealDetailActivity::class.java).putExtras(bundle))
        }

        categoriesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putString(SELECTED_VALUE,it.strCategory)
            }
            startActivity(Intent(activity,MealListActivity::class.java).putExtras(bundle))
        }
    }

    private fun setUpTrendingRecipesRecyclerView(){
        trendingRecipesAdapter = TrendingRecipesAdapter()
        binding.rvTrendingRecipes.apply {
            adapter = trendingRecipesAdapter
            layoutManager = GridLayoutManager(context,1,GridLayoutManager.HORIZONTAL,false)
        }
    }

    private fun setUpCategoryAdapter(){
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }

}