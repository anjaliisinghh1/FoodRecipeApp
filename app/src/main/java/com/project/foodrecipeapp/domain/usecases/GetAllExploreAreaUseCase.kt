package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetAllExploreAreaUseCase(
    private val repository: FoodRecipeRepository
){
    suspend fun execute(allArea: String) = repository.getAllExploreArea(allArea)
}