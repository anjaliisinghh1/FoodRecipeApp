package com.project.foodrecipeapp.data.repositoryImpl

import com.project.foodrecipeapp.data.remote.FoodRecipeApi
import com.project.foodrecipeapp.data.remote.dto.*
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

    override suspend fun getFoodByCategory(category: String): Response<AllMealDTO> {
        return api.getFoodByCategory(category)
    }

    override suspend fun getFoodByArea(area: String): Response<AllMealDTO> {
        return api.getFoodByArea(area)
    }

    override suspend fun getFoodByIngredients(ingredient: String): Response<AllMealDTO> {
        return api.getFoodByIngredients(ingredient)
    }

    override suspend fun getAllCategories(): Response<CategoriesDTO> {
        return api.getAllCategories()
    }

    override suspend fun getAllExploreCategories(allExploreCategories: String): Response<ExploreCategoriesDTO> {
        return api.getAllExploreCategories(allExploreCategories)
    }

    override suspend fun getAllExploreArea(allArea: String): Response<ExploreAreaDTO> {
        return api.getAllExploreArea(allArea)
    }

    override suspend fun getAllExploreIngredients(allIngredients: String): Response<ExploreIngredientsDTO> {
        return api.getAllExploreIngredients(allIngredients)
    }

    override suspend fun getSearchMeal(search: String): Response<AllMealDTO> {
        return api.getSearchMeal(search)
    }

}