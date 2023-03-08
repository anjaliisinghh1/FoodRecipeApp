package com.project.foodrecipeapp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.foodrecipeapp.common.Constants.EMPTY
import com.project.foodrecipeapp.presentation.fragments.IngredientsFragment
import com.project.foodrecipeapp.presentation.fragments.InstructionsFragment

class MealDetailTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,val mealName: String = EMPTY,val mealImage: String = EMPTY,val mealId: String = EMPTY) : FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> IngredientsFragment.values(mealName,mealImage,mealId)
            1 -> InstructionsFragment.values(mealName,mealImage,mealId)
            else -> IngredientsFragment.values(mealName,mealImage,mealId)
        }
    }
}