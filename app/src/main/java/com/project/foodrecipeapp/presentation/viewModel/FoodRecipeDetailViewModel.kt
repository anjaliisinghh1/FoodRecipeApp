package com.project.foodrecipeapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodrecipeapp.common.toMealListModel
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.domain.usecases.GetFoodRecipeDetailByIdUseCase
import kotlinx.coroutines.launch

class FoodRecipeDetailViewModel(
    private val getFoodRecipeDetailByIdUseCase: GetFoodRecipeDetailByIdUseCase
) : ViewModel(){

    private val _recipeDetailLiveData = MutableLiveData<MealsList>()
    val recipeDetailLiveData : LiveData<MealsList>
        get() =_recipeDetailLiveData

    fun getRecipeDetail(id: String){
        viewModelScope.launch {
            val recipe = getFoodRecipeDetailByIdUseCase.execute(id)
            if (recipe.isSuccessful){
                _recipeDetailLiveData.postValue(recipe.body()?.toMealListModel())
            }
        }
    }

}