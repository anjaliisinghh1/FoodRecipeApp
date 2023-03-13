package com.project.foodrecipeapp.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.project.foodrecipeapp.MyApplication
import com.project.foodrecipeapp.common.Constants.TRENDING_RECIPE
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.common.toAllMealModel
import com.project.foodrecipeapp.common.toCategoriesModel
import com.project.foodrecipeapp.common.toMealListModel
import com.project.foodrecipeapp.data.remote.dto.AllMealDTO
import com.project.foodrecipeapp.data.remote.dto.CategoriesDTO
import com.project.foodrecipeapp.data.remote.dto.MealListDTO
import com.project.foodrecipeapp.domain.model.AllMeal
import com.project.foodrecipeapp.domain.model.Categories
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.domain.usecases.GetAllCategoriesUseCase
import com.project.foodrecipeapp.domain.usecases.GetFoodByCategoryUseCase
import com.project.foodrecipeapp.domain.usecases.GetRandomMealUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HomeFoodViewModel(
    application: Application,
    private val getRandomMealUseCase: GetRandomMealUseCase,
    private val getFoodByCategoryUseCase: GetFoodByCategoryUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : AndroidViewModel(application){

    private val _randomMealLiveData = MutableLiveData<Resource<MealsList>>()
    val randomMealLiveData : LiveData<Resource<MealsList>>
    get() =_randomMealLiveData

    private val _trendingRecipesLiveData = MutableLiveData<Resource<AllMeal>>()
    val trendingRecipesLiveData : LiveData<Resource<AllMeal>>
        get() =_trendingRecipesLiveData

    private val _categoriesLiveData = MutableLiveData<Resource<Categories>>()
    val categoriesLiveData : LiveData<Resource<Categories>>
        get() =_categoriesLiveData

    init {
        getRandomMeal()
        getTrendingRecipes(TRENDING_RECIPE)
        getCategories()
    }

    fun getRandomMeal(){
        viewModelScope.launch {
            _randomMealLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val meal = getRandomMealUseCase.execute()
                    _randomMealLiveData.postValue(handlingRandomMealResponse(meal))
                } else {
                    _randomMealLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _randomMealLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _randomMealLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getTrendingRecipes(category: String){
        viewModelScope.launch {
            _trendingRecipesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val recipes = getFoodByCategoryUseCase.execute(category)
                    _trendingRecipesLiveData.postValue(handlingTrendingRecipesResponse(recipes))
                } else {
                    _trendingRecipesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _trendingRecipesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _trendingRecipesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            _categoriesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val categories = getAllCategoriesUseCase.execute()
                    _categoriesLiveData.postValue(handlingCategoriesResponse(categories))
                } else {
                    _categoriesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _categoriesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _categoriesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    private fun handlingRandomMealResponse(response: Response<MealListDTO>): Resource<MealsList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toMealListModel())
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlingTrendingRecipesResponse(response: Response<AllMealDTO>): Resource<AllMeal> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toAllMealModel())
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlingCategoriesResponse(response: Response<CategoriesDTO>): Resource<Categories> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toCategoriesModel())
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