package com.project.foodrecipeapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodrecipeapp.common.toAllMealModel
import com.project.foodrecipeapp.domain.model.AllMeal
import com.project.foodrecipeapp.domain.usecases.GetFoodByAreaUseCase
import com.project.foodrecipeapp.domain.usecases.GetFoodByCategoryUseCase
import com.project.foodrecipeapp.domain.usecases.GetFoodByIngredientUseCase
import kotlinx.coroutines.launch

class MealListViewModel(
    private val getFoodByCategoryUseCase: GetFoodByCategoryUseCase,
    private val getFoodByAreaUseCase: GetFoodByAreaUseCase,
    private val getFoodByIngredientUseCase: GetFoodByIngredientUseCase
) : ViewModel(){

    private val _RecipesLiveData = MutableLiveData<AllMeal>()
    val RecipesLiveData : LiveData<AllMeal>
        get() =_RecipesLiveData

    fun getRecipesByCategory(category: String){
        viewModelScope.launch {
            val categoryRecipes = getFoodByCategoryUseCase.execute(category)
            val areaRecipes = getFoodByAreaUseCase.execute(category)
            val ingredientRecipes = getFoodByIngredientUseCase.execute(category)

            if (!categoryRecipes.body()?.meals.isNullOrEmpty() && categoryRecipes.isSuccessful){
                _RecipesLiveData.postValue(categoryRecipes.body()?.toAllMealModel())

            }else if (!areaRecipes.body()?.meals.isNullOrEmpty() && areaRecipes.isSuccessful){
                _RecipesLiveData.postValue(areaRecipes.body()?.toAllMealModel())

            }else if (!ingredientRecipes.body()?.meals.isNullOrEmpty() && ingredientRecipes.isSuccessful){
                _RecipesLiveData.postValue(ingredientRecipes.body()?.toAllMealModel())
            }

        }

    }
}