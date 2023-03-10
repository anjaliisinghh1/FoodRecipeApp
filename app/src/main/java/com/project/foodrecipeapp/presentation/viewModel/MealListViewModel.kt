package com.project.foodrecipeapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodrecipeapp.common.toAllMealModel
import com.project.foodrecipeapp.domain.model.AllMeal
import com.project.foodrecipeapp.domain.usecases.GetFoodByCategoryUseCase
import kotlinx.coroutines.launch

class MealListViewModel(
    private val getFoodByCategoryUseCase: GetFoodByCategoryUseCase
) : ViewModel(){

    private val _RecipesLiveData = MutableLiveData<AllMeal>()
    val RecipesLiveData : LiveData<AllMeal>
        get() =_RecipesLiveData

    fun getRecipesByCategory(category: String){
        viewModelScope.launch {
            val recipes = getFoodByCategoryUseCase.execute(category)
            if (recipes.isSuccessful){
                _RecipesLiveData.postValue(recipes.body()?.toAllMealModel())
            }
        }

    }
}