package com.project.foodrecipeapp.data.remote

import com.project.foodrecipeapp.data.remote.dto.*
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

    @GET("filter.php?")
    suspend fun getFoodByArea(@Query("a") area: String): Response<AllMealDTO>

    @GET("filter.php?")
    suspend fun getFoodByIngredients(@Query("i") ingredient: String): Response<AllMealDTO>

    @GET("categories.php")
    suspend fun getAllCategories(): Response<CategoriesDTO>

    @GET("list.php?")
    suspend fun getAllExploreCategories(@Query("c") allExploreCategories: String): Response<ExploreCategoriesDTO>

    @GET("list.php?")
    suspend fun getAllExploreArea(@Query("a") allArea: String): Response<ExploreAreaDTO>

    @GET("list.php?")
    suspend fun getAllExploreIngredients(@Query("i") allIngredients: String): Response<ExploreIngredientsDTO>

    @GET("search.php?")
    suspend fun getSearchMeal(@Query("s") search: String): Response<AllMealDTO>
}