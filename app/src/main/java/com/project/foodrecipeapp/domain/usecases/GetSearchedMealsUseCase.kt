package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetSearchedMealsUseCase(
    private val repository: FoodRecipeRepository
) {
    suspend fun execute(search: String) = repository.getSearchMeal(search)
}