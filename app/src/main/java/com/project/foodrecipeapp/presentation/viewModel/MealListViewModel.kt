package com.project.foodrecipeapp.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.project.foodrecipeapp.MyApplication
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.common.toAllMealModel
import com.project.foodrecipeapp.data.remote.dto.AllMealDTO
import com.project.foodrecipeapp.domain.model.AllMeal
import com.project.foodrecipeapp.domain.usecases.GetFoodByAreaUseCase
import com.project.foodrecipeapp.domain.usecases.GetFoodByCategoryUseCase
import com.project.foodrecipeapp.domain.usecases.GetFoodByIngredientUseCase
import com.project.foodrecipeapp.domain.usecases.GetSearchedMealsUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MealListViewModel(
    application: Application,
    private val getFoodByCategoryUseCase: GetFoodByCategoryUseCase,
    private val getFoodByAreaUseCase: GetFoodByAreaUseCase,
    private val getFoodByIngredientUseCase: GetFoodByIngredientUseCase,
    private val getSearchedMealsUseCase: GetSearchedMealsUseCase
) : AndroidViewModel(application) {

    private val _recipesLiveData = MutableLiveData<Resource<AllMeal>>()
    val recipesLiveData: LiveData<Resource<AllMeal>>
        get() = _recipesLiveData

    fun getRecipesByCategory(category: String) {
        viewModelScope.launch {
            _recipesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val categoryRecipes = getFoodByCategoryUseCase.execute(category)
                    _recipesLiveData.postValue(handlingResponse(categoryRecipes))
                } else {
                    _recipesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _recipesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _recipesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getRecipesByArea(area: String) {
        viewModelScope.launch {
            _recipesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val areaRecipes = getFoodByAreaUseCase.execute(area)
                    _recipesLiveData.postValue(handlingResponse(areaRecipes))
                } else {
                    _recipesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _recipesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _recipesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getRecipesByIngredients(ingredient: String) {
        viewModelScope.launch {
            _recipesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val ingredientRecipes = getFoodByIngredientUseCase.execute(ingredient)
                    _recipesLiveData.postValue(handlingResponse(ingredientRecipes))
                } else {
                    _recipesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _recipesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _recipesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getSearchedMeals(search: String){
        viewModelScope.launch {
            _recipesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val meal = getSearchedMealsUseCase.execute(search)
                    _recipesLiveData.postValue(handlingResponse(meal))
                } else {
                    _recipesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _recipesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _recipesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    private fun handlingResponse(response: Response<AllMealDTO>): Resource<AllMeal> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toAllMealModel())
            }
        }
        return Resource.Error(response.message())
    }

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}