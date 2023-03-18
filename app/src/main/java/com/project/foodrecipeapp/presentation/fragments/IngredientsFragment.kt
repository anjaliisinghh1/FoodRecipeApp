package com.project.foodrecipeapp.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.project.foodrecipeapp.R
import com.project.foodrecipeapp.common.Constants.EMPTY
import com.project.foodrecipeapp.common.Constants.MEAL_ID
import com.project.foodrecipeapp.common.Constants.MEAL_IMG_URL
import com.project.foodrecipeapp.common.Constants.MEAL_NAME
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.common.loadImage
import com.project.foodrecipeapp.common.shareNews
import com.project.foodrecipeapp.databinding.FragmentIngredientsBinding
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.presentation.viewModel.FoodRecipeDetailViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class IngredientsFragment : Fragment(R.layout.fragment_ingredients){

    private lateinit var binding: FragmentIngredientsBinding
    private val viewModel : FoodRecipeDetailViewModel  by viewModel()
    private var mealName: String = EMPTY
    private var mealImage: String = EMPTY
    private var mealId: String = EMPTY
    private lateinit var mealDetail : MealsList
    private var ingredientContent: String = EMPTY

    companion object{
        fun values(name: String,image: String ,id: String): IngredientsFragment{
            val bundle = Bundle().apply {
                putString(MEAL_NAME,name)
                putString(MEAL_IMG_URL,image)
                putString(MEAL_ID,id)
            }
            val fragment = IngredientsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentIngredientsBinding.bind(view)

        arguments?.let {
            mealName = it.getString(MEAL_NAME) ?: EMPTY
            mealImage = it.getString(MEAL_IMG_URL) ?: EMPTY
            mealId = it.getString(MEAL_ID) ?: EMPTY
        }

        if (!mealId.isNullOrEmpty()){
            viewModel.getRecipeDetail(mealId)
        }

        observeLiveData()
        onClickListener()

    }

    private fun observeLiveData(){
        viewModel.recipeDetailLiveData.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        mealDetail = it
                        setIngredientsDetailData(it)
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

    private fun setIngredientsDetailData(mealsList: MealsList){
        loadImage(binding.foodImage,mealsList.meals[0].strMealThumb)
        binding.collapsingToolbarLayout.title = mealsList.meals[0].strMeal
        setIngredientsContent(mealsList)
        binding.tvContent.text = ingredientContent
    }

    private fun bullet(ingredient: String?): String {
        return if (!ingredient.isNullOrEmpty()){
            "\u2022 "
        }else{
            ""
        }

    }

    private fun setIngredientsContent(mealsList: MealsList){
        mealsList.meals[0].let {
            ingredientContent = "\u2022 " + it.strIngredient1 + "\t" + it.strMeasure1 +
                    "\n" + bullet(it.strIngredient2) + it.strIngredient2 + "\t" + it.strMeasure2 +
                    "\n" + bullet(it.strIngredient3) + it.strIngredient3 + "\t" + it.strMeasure3 +
                    "\n" + bullet(it.strIngredient4) + it.strIngredient4 + "\t" + it.strMeasure4 +
                    "\n" + bullet(it.strIngredient5) + it.strIngredient5 + "\t" + it.strMeasure5 +
                    "\n" + bullet(it.strIngredient6) + it.strIngredient6 + "\t" + it.strMeasure6 +
                    "\n" + bullet(it.strIngredient7) + it.strIngredient7 + "\t" + it.strMeasure7 +
                    "\n" + bullet(it.strIngredient8) + it.strIngredient8 + "\t" + it.strMeasure8 +
                    "\n" + bullet(it.strIngredient9) + it.strIngredient9 + "\t" + it.strMeasure9 +
                    "\n" + bullet(it.strIngredient10) + it.strIngredient10 + "\t" + it.strMeasure10 +
                    "\n" + bullet(it.strIngredient11) + it.strIngredient11 + "\t" + it.strMeasure11 +
                    "\n" + bullet(it.strIngredient12) + it.strIngredient12 + "\t" + it.strMeasure12 +
                    "\n" + bullet(it.strIngredient13) + it.strIngredient13 + "\t" + it.strMeasure13 +
                    "\n" + bullet(it.strIngredient14) + it.strIngredient14 + "\t" + it.strMeasure14 +
                    "\n" + bullet(it.strIngredient15) + it.strIngredient15 + "\t" + it.strMeasure15 +
                    "\n" + bullet(it.strIngredient16) + it.strIngredient16 + "\t" + it.strMeasure16 +
                    "\n" + bullet(it.strIngredient17) + it.strIngredient17 + "\t" + it.strMeasure17 +
                    "\n" + bullet(it.strIngredient18) + it.strIngredient18 + "\t" + it.strMeasure18 +
                    "\n" + bullet(it.strIngredient19) + it.strIngredient19 + "\t" + it.strMeasure19 +
                    "\n" + bullet(it.strIngredient20) + it.strIngredient20 + "\t" + it.strMeasure20
        }
    }

    private fun onClickListener(){
        binding.shareIcon.setOnClickListener {
            shareNews(context,mealDetail.meals[0])
        }

        binding.youtubeIcon.setOnClickListener {
            val youtubeUrl = mealDetail.meals[0].strYoutube
            if (!youtubeUrl.isNullOrEmpty()){
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)))
            }
        }
    }

    private fun hideProgressBar(){
        binding.detailProgressBar.visibility = View.GONE
        binding.tvLabelInstructions.visibility = View.VISIBLE
        binding.youtubeIcon.visibility = View.VISIBLE
        binding.shareIcon.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        binding.detailProgressBar.visibility = View.VISIBLE
        binding.tvLabelInstructions.visibility = View.GONE
        binding.youtubeIcon.visibility = View.GONE
        binding.shareIcon.visibility = View.GONE
    }
}