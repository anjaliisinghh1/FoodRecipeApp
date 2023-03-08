package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetFoodRecipeDetailByIdUseCase(
    private val repository: FoodRecipeRepository
) {
    suspend fun execute(id: String) = repository.getFoodRecipeDetailById(id)
}