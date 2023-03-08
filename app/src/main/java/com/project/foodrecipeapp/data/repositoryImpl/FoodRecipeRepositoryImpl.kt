package com.project.foodrecipeapp.data.repositoryImpl

import com.project.foodrecipeapp.common.Resource
import com.project.foodrecipeapp.data.remote.FoodRecipeApi
import com.project.foodrecipeapp.data.remote.dto.MealListDTO
import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository
import retrofit2.Response

class FoodRecipeRepositoryImpl(
    private val api: FoodRecipeApi
) : FoodRecipeRepository {

    override suspend fun getRandomMeal(): Response<MealListDTO> {
        return api.getRandomMeal()
    }

    override suspend fun getFoodRecipeDetailById(id: String): Response<MealListDTO> {
        return api.getFoodRecipeDetailById(id)
    }

}