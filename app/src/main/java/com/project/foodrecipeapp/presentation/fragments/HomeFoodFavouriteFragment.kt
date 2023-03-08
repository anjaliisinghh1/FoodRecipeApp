package com.project.foodrecipeapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.databinding.FragmentHomeFoodFavouriteBinding

class HomeFoodFavouriteFragment : Fragment(R.layout.fragment_home_food_favourite) {

    private lateinit var binding: FragmentHomeFoodFavouriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeFoodFavouriteBinding.bind(view)
    }
}