package com.project.foodrecipeapp.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.project.foodrecipeapp.MyApplication
import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.common.toMealListModel
import com.project.foodrecipeapp.data.remote.dto.MealListDTO
import com.project.foodrecipeapp.domain.model.MealsList
import com.project.foodrecipeapp.domain.usecases.GetFoodRecipeDetailByIdUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class FoodRecipeDetailViewModel(
    application: Application,
    private val getFoodRecipeDetailByIdUseCase: GetFoodRecipeDetailByIdUseCase
) : AndroidViewModel(application){

    private val _recipeDetailLiveData = MutableLiveData<Resource<MealsList>>()
    val recipeDetailLiveData : LiveData<Resource<MealsList>>
        get() =_recipeDetailLiveData

    fun getRecipeDetail(id: String){
        viewModelScope.launch {
            _recipeDetailLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()){
                    val recipe = getFoodRecipeDetailByIdUseCase.execute(id)
                    _recipeDetailLiveData.postValue(handlingResponse(recipe))
                }else {
                    _recipeDetailLiveData.postValue(Resource.Error("No Internet Connection"))
                }

            }catch (t: Throwable) {
                when (t) {
                    is IOException -> _recipeDetailLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _recipeDetailLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    private fun handlingResponse(response: Response<MealListDTO>): Resource<MealsList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toMealListModel())
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