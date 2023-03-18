package com.project.foodrecipeapp.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.Constants
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.databinding.FragmentInstructionsBinding
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.presentation.viewModel.FoodRecipeDetailViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class InstructionsFragment : Fragment(R.layout.fragment_instructions){

    private lateinit var binding: FragmentInstructionsBinding
    private val viewModel : FoodRecipeDetailViewModel by viewModel()
    private var mealName: String = Constants.EMPTY
    private var mealImage: String = Constants.EMPTY
    private var mealId: String = Constants.EMPTY
    private lateinit var mealDetail : MealsList

    companion object{
        fun values(name: String,image: String ,id: String): InstructionsFragment{
            val bundle = Bundle().apply {
                putString(Constants.MEAL_NAME,name)
                putString(Constants.MEAL_IMG_URL,image)
                putString(Constants.MEAL_ID,id)
            }
            val fragment = InstructionsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInstructionsBinding.bind(view)

        arguments?.let {
            mealName = it.getString(Constants.MEAL_NAME) ?: Constants.EMPTY
            mealImage = it.getString(Constants.MEAL_IMG_URL) ?: Constants.EMPTY
            mealId = it.getString(Constants.MEAL_ID) ?: Constants.EMPTY
        }

        if (!mealId.isNullOrEmpty()){
            viewModel.getRecipeDetail(mealId)
        }

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.recipeDetailLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        mealDetail = it
                        setInstructionsDetailData(it)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An Error Occured $message", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setInstructionsDetailData(mealsList: MealsList){
        binding.tvContent.text = mealsList.meals[0].strInstructions
    }

    private fun hideProgressBar(){
        binding.ingredientDetailProgressBar.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.ingredientDetailProgressBar.visibility = View.VISIBLE
    }
}