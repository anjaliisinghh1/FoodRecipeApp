package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetFoodByAreaUseCase(
    private val repository: FoodRecipeRepository
) {
    suspend fun execute(area: String) = repository.getFoodByArea(area)
}