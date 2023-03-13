package com.project.foodrecipeapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.foodrecipeapp.common.Constants.EXPLORE_LIST
import com.project.foodrecipeapp.common.toExploreAreaModel
import com.project.foodrecipeapp.common.toExploreCategoriesModel
import com.project.foodrecipeapp.common.toExploreIngredientsModel
import com.project.foodrecipeapp.domain.model.ExploreArea
import com.project.foodrecipeapp.domain.model.ExploreCategories
import com.project.foodrecipeapp.domain.model.ExploreIngredients
import com.project.foodrecipeapp.domain.usecases.GetAllExploreAreaUseCase
import com.project.foodrecipeapp.domain.usecases.GetAllExploreCategoriesUseCase
import com.project.foodrecipeapp.domain.usecases.GetAllExploreIngredientsUseCase
import kotlinx.coroutines.launch

class HomeFoodExploreViewModel(
    private val getAllExploreCategoriesUseCase: GetAllExploreCategoriesUseCase,
    private val getAllExploreAreaUseCase: GetAllExploreAreaUseCase,
    private val getAllExploreIngredientsUseCase: GetAllExploreIngredientsUseCase
): ViewModel(){

    private val _allExploreCategoriesLiveData = MutableLiveData<ExploreCategories>()
    val allExploreCategoriesLiveData : LiveData<ExploreCategories>
        get() =_allExploreCategoriesLiveData

    private val _allExploreAreaLiveData = MutableLiveData<ExploreArea>()
    val allExploreAreaLiveData : LiveData<ExploreArea>
        get() =_allExploreAreaLiveData

    private val _allExploreIngredientsLiveData = MutableLiveData<ExploreIngredients>()
    val allExploreIngredientsLiveData : LiveData<ExploreIngredients>
        get() =_allExploreIngredientsLiveData

    init {
        getAllExploreCategories(EXPLORE_LIST)
        getAllExploreArea(EXPLORE_LIST)
        getAllExploreIngredients(EXPLORE_LIST)
    }

    fun getAllExploreCategories(allExploreCategories: String){
        viewModelScope.launch {
            val categories = getAllExploreCategoriesUseCase.execute(allExploreCategories)
            if (categories.isSuccessful){
                _allExploreCategoriesLiveData.postValue(categories.body()?.toExploreCategoriesModel())
            }
        }
    }

    fun getAllExploreArea(allExploreArea: String){
        viewModelScope.launch{
            val area = getAllExploreAreaUseCase.execute(allExploreArea)
            if (area.isSuccessful){
                _allExploreAreaLiveData.postValue(area.body()?.toExploreAreaModel())
            }
        }
    }

    fun getAllExploreIngredients(allExploreIngredients: String){
        viewModelScope.launch {
            val ingredients = getAllExploreIngredientsUseCase.execute(allExploreIngredients)
            if (ingredients.isSuccessful){
                _allExploreIngredientsLiveData.postValue(ingredients.body()?.toExploreIngredientsModel())
            }
        }
    }

}