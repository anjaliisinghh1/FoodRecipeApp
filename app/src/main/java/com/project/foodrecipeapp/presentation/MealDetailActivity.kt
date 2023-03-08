package com.project.foodrecipeapp.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.project.foodrecipeapp.common.Constants
import com.project.foodrecipeapp.common.Constants.EMPTY
import com.project.foodrecipeapp.common.Constants.MEAL_ID
import com.project.foodrecipeapp.common.Constants.MEAL_IMG_URL
import com.project.foodrecipeapp.common.Constants.MEAL_NAME
import com.project.foodrecipeapp.databinding.ActivityMealDetailBinding
import com.project.foodrecipeapp.presentation.adapter.MealDetailTabAdapter

class MealDetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMealDetailBinding
    private var tabsList = arrayListOf<String>(Constants.INGREDIENT, Constants.INSTRUCTIONS)
    private var mealName: String = EMPTY
    private var mealId : String = EMPTY
    private var mealImage : String = EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealName = intent.getStringExtra(MEAL_NAME) ?: EMPTY
        mealImage = intent.getStringExtra(MEAL_IMG_URL) ?: EMPTY
        mealId = intent.getStringExtra(MEAL_ID) ?: EMPTY

        binding.viewPager.adapter = MealDetailTabAdapter(supportFragmentManager,lifecycle,mealName,mealImage,mealId)

        TabLayoutMediator(binding.tabLayout,binding.viewPager){
            tab,position ->
                tab.text = tabsList[position]
        }.attach()

    }

}