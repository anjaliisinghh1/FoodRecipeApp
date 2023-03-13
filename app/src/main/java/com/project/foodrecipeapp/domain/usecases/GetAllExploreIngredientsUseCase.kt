package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetAllExploreIngredientsUseCase(
    private val repository: FoodRecipeRepository
){
    suspend fun execute(allIngredients: String) = repository.getAllExploreIngredients(allIngredients)
}