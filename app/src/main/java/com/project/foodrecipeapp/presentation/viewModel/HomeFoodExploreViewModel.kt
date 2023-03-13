package com.project.foodrecipeapp.presentation.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.project.foodrecipeapp.MyApplication
import com.project.foodrecipeapp.common.*
import com.project.foodrecipeapp.common.Constants.EXPLORE_LIST
import com.project.foodrecipeapp.data.remote.dto.ExploreAreaDTO
import com.project.foodrecipeapp.data.remote.dto.ExploreCategoriesDTO
import com.project.foodrecipeapp.data.remote.dto.ExploreIngredientsDTO
import com.project.foodrecipeapp.domain.model.ExploreArea
import com.project.foodrecipeapp.domain.model.ExploreCategories
import com.project.foodrecipeapp.domain.model.ExploreIngredients
import com.project.foodrecipeapp.domain.usecases.GetAllExploreAreaUseCase
import com.project.foodrecipeapp.domain.usecases.GetAllExploreCategoriesUseCase
import com.project.foodrecipeapp.domain.usecases.GetAllExploreIngredientsUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HomeFoodExploreViewModel(
    application: Application,
    private val getAllExploreCategoriesUseCase: GetAllExploreCategoriesUseCase,
    private val getAllExploreAreaUseCase: GetAllExploreAreaUseCase,
    private val getAllExploreIngredientsUseCase: GetAllExploreIngredientsUseCase
): AndroidViewModel(application){

    private val _allExploreCategoriesLiveData = MutableLiveData<Resource<ExploreCategories>>()
    val allExploreCategoriesLiveData : LiveData<Resource<ExploreCategories>>
        get() =_allExploreCategoriesLiveData

    private val _allExploreAreaLiveData = MutableLiveData<Resource<ExploreArea>>()
    val allExploreAreaLiveData : LiveData<Resource<ExploreArea>>
        get() =_allExploreAreaLiveData

    private val _allExploreIngredientsLiveData = MutableLiveData<Resource<ExploreIngredients>>()
    val allExploreIngredientsLiveData : LiveData<Resource<ExploreIngredients>>
        get() =_allExploreIngredientsLiveData

    init {
        getAllExploreCategories(EXPLORE_LIST)
        getAllExploreArea(EXPLORE_LIST)
        getAllExploreIngredients(EXPLORE_LIST)
    }

    fun getAllExploreCategories(allExploreCategories: String){
        viewModelScope.launch {
            _allExploreCategoriesLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()) {
                    val categories = getAllExploreCategoriesUseCase.execute(allExploreCategories)
                    _allExploreCategoriesLiveData.postValue(handlingExploreCategoriesResponse(categories))
                }else {
                    _allExploreCategoriesLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable){
                when (t) {
                    is IOException -> _allExploreCategoriesLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _allExploreCategoriesLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getAllExploreArea(allExploreArea: String){
        viewModelScope.launch{
            _allExploreAreaLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()){
                    val area = getAllExploreAreaUseCase.execute(allExploreArea)
                    _allExploreAreaLiveData.postValue(handlingExploreAreaResponse(area))
                }else{
                    _allExploreAreaLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable){
                when (t) {
                    is IOException -> _allExploreAreaLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _allExploreAreaLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    fun getAllExploreIngredients(allExploreIngredients: String){
        viewModelScope.launch {
            _allExploreIngredientsLiveData.postValue(Resource.Loading())
            try {
                if (hasInternetConnection()){
                    val ingredients = getAllExploreIngredientsUseCase.execute(allExploreIngredients)
                    _allExploreIngredientsLiveData.postValue(handlingExploreIngredientsResponse(ingredients))
                }else{
                    _allExploreIngredientsLiveData.postValue(Resource.Error("No Internet Connection"))
                }
            } catch (t: Throwable){
                when (t) {
                    is IOException -> _allExploreIngredientsLiveData.postValue(Resource.Error("Network Failure"))
                    else -> _allExploreIngredientsLiveData.postValue(Resource.Error("Conversion Error"))
                }
            }
        }
    }

    private fun handlingExploreCategoriesResponse(response: Response<ExploreCategoriesDTO>): Resource<ExploreCategories> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toExploreCategoriesModel())
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlingExploreAreaResponse(response: Response<ExploreAreaDTO>): Resource<ExploreArea> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toExploreAreaModel())
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlingExploreIngredientsResponse(response: Response<ExploreIngredientsDTO>): Resource<ExploreIngredients> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse.toExploreIngredientsModel())
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