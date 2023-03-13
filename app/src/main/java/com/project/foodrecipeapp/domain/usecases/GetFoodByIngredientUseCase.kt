package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetFoodByIngredientUseCase(
    private val repository: FoodRecipeRepository
) {
    suspend fun execute(ingredient: String) = repository.getFoodByIngredients(ingredient)
}