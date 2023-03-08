package com.project.foodrecipeapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.common.toMealListModel
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository
import com.project.foodrecipeapp.domain.usecases.GetRandomMealUseCase
import kotlinx.coroutines.launch

class HomeFoodViewModel(
    private val getRandomMealUseCase: GetRandomMealUseCase
) : ViewModel(){

    private val _randomMealLiveData = MutableLiveData<MealsList>()
    val randomMealLiveData : LiveData<MealsList>
    get() =_randomMealLiveData

    fun getRandomMeal(){
        viewModelScope.launch {
            val meal = getRandomMealUseCase.execute()
            if (meal.isSuccessful){
                _randomMealLiveData.postValue(meal.body()?.toMealListModel())
            }
        }
    }
}