package com.project.foodrecipeapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.databinding.FragmentHomeFoodExploreBinding

class HomeFoodExploreFragment : Fragment(R.layout.fragment_home_food_explore) {

    private lateinit var binding: FragmentHomeFoodExploreBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFoodExploreBinding.bind(view)
    }
}