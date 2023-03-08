package com.project.foodrecipeapp.presentation.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.Constants.MEAL_ID
import com.project.foodrecipeapp.common.Constants.MEAL_IMG_URL
import com.project.foodrecipeapp.common.Constants.MEAL_NAME
import com.project.foodrecipeapp.common.loadImage
import com.project.foodrecipeapp.databinding.FragmentHomeFoodBinding
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.presentation.MealDetailActivity
import com.project.foodrecipeapp.presentation.viewModel.HomeFoodViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFoodFragment : Fragment(R.layout.fragment_home_food){

    private lateinit var binding: FragmentHomeFoodBinding
    private val viewModel: HomeFoodViewModel by viewModel()
    private lateinit var meal : MealsList

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFoodBinding.bind(view)
        viewModel.getRandomMeal()
        observeLiveData()
        onRandomMealClick()

    }

    private fun observeLiveData(){
        viewModel.randomMealLiveData.observe(viewLifecycleOwner, Observer {
            meal = it
            val randomMeal = it.meals[0]
            if (!randomMeal.strMealThumb.isNullOrEmpty()){
                loadImage(binding.ivRandomFood,randomMeal.strMealThumb)
            }

        })
    }

    private fun onRandomMealClick(){
        binding.randomImgCardView.setOnClickListener {
            val mealData = meal.meals[0]
            val bundle = Bundle().apply {
                putString(MEAL_NAME,mealData.strMeal)
                putString(MEAL_IMG_URL,mealData.strMealThumb)
                putString(MEAL_ID,mealData.idMeal)
            }
            startActivity(Intent(activity,MealDetailActivity::class.java).putExtras(bundle))
        }
    }
}