package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetRandomMealUseCase(
    private val repository: FoodRecipeRepository
) {

    suspend fun execute() = repository.getRandomMeal()

}