package com.project.foodrecipeapp.domain.repository

import com.project.foodrecipeapp.data.remote.dto.*
import retrofit2.Response

interface FoodRecipeRepository {

    suspend fun getRandomMeal(): Response<MealListDTO>

    suspend fun getFoodRecipeDetailById(id: String): Response<MealListDTO>

    suspend fun getFoodByCategory(category: String): Response<AllMealDTO>

    suspend fun getFoodByArea(area: String): Response<AllMealDTO>

    suspend fun getFoodByIngredients(ingredient: String): Response<AllMealDTO>

    suspend fun getAllCategories(): Response<CategoriesDTO>

    suspend fun getAllExploreCategories(allExploreCategories: String): Response<ExploreCategoriesDTO>

    suspend fun getAllExploreArea(allArea: String): Response<ExploreAreaDTO>

    suspend fun getAllExploreIngredients(allIngredients: String): Response<ExploreIngredientsDTO>

}