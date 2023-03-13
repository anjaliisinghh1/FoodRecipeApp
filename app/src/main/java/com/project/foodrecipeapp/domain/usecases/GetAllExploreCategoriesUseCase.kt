package com.project.foodrecipeapp.domain.usecases

import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository

class GetAllExploreCategoriesUseCase(
    private val repository: FoodRecipeRepository
){
    suspend fun execute(allExploreCategories: String) = repository.getAllExploreCategories(allExploreCategories)
}