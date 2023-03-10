package com.project.foodrecipeapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.common.toAllMealModel
import com.project.foodrecipeapp.common.toCategoriesModel
import com.project.foodrecipeapp.common.toMealListModel
import com.project.foodrecipeapp.domain.model.AllMeal
import com.project.foodrecipeapp.domain.model.Categories
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository
import com.project.foodrecipeapp.domain.usecases.GetAllCategoriesUseCase
import com.project.foodrecipeapp.domain.usecases.GetFoodByCategoryUseCase
import com.project.foodrecipeapp.domain.usecases.GetRandomMealUseCase
import kotlinx.coroutines.launch

class HomeFoodViewModel(
    private val getRandomMealUseCase: GetRandomMealUseCase,
    private val getFoodByCategoryUseCase: GetFoodByCategoryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel(){

    private val _randomMealLiveData = MutableLiveData<MealsList>()
    val randomMealLiveData : LiveData<MealsList>
    get() =_randomMealLiveData

    private val _trendingRecipesLiveData = MutableLiveData<AllMeal>()
    val trendingRecipesLiveData : LiveData<AllMeal>
        get() =_trendingRecipesLiveData

    private val _categoriesLiveData = MutableLiveData<Categories>()
    val categoriesLiveData : LiveData<Categories>
        get() =_categoriesLiveData

    init {
        getRandomMeal()
        getTrendingRecipes("Seafood")
        getCategories()
    }

    fun getRandomMeal(){
        viewModelScope.launch {
            val meal = getRandomMealUseCase.execute()
            if (meal.isSuccessful){
                _randomMealLiveData.postValue(meal.body()?.toMealListModel())
            }
        }
    }

    fun getTrendingRecipes(category: String){
        viewModelScope.launch {
            val recipes = getFoodByCategoryUseCase.execute(category)
            if (recipes.isSuccessful){
                _trendingRecipesLiveData.postValue(recipes.body()?.toAllMealModel())
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            val categories = getAllCategoriesUseCase.execute()
            if (categories.isSuccessful){
                _categoriesLiveData.postValue(categories.body()?.toCategoriesModel())
            }
        }
    }
}