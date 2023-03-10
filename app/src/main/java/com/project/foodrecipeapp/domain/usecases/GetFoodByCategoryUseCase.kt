package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetFoodByCategoryUseCase(
    private val repository: FoodRecipeRepository
) {
    suspend fun execute(category: String) = repository.getFoodByCategory(category)
}