package com.project.foodrecipeapp.data.remote

import com.project.foodrecipeapp.data.remote.dto.AllMealDTO
import com.project.foodrecipeapp.data.remote.dto.CategoriesDTO
import com.project.foodrecipeapp.data.remote.dto.MealListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodRecipeApi {

    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealListDTO>

    @GET("lookup.php?")
    suspend fun getFoodRecipeDetailById(@Query("i") id: String): Response<MealListDTO>

    @GET("filter.php?")
    suspend fun getFoodByCategory(@Query("c") category: String): Response<AllMealDTO>

    @GET("categories.php")
    suspend fun getAllCategories(): Response<CategoriesDTO>
}