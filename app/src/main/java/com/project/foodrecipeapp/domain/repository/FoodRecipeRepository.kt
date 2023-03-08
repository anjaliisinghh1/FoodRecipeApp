package com.project.foodrecipeapp.domain.repository

import com.project.foodrecipeapp.data.remote.dto.MealListDTO
import retrofit2.Response

interface FoodRecipeRepository {

    suspend fun getRandomMeal(): Response<MealListDTO>

    suspend fun getFoodRecipeDetailById(id: String): Response<MealListDTO>

}