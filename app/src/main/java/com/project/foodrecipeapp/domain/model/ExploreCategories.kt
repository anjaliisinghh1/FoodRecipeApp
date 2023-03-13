package com.project.foodrecipeapp.domain.model

import com.project.foodrecipeapp.data.remote.dto.ExploreCategoriesDetailDTO

data class ExploreCategories(
    val meals: List<ExploreCategoriesDetail>
)