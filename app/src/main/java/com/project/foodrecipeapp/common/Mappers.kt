package com.project.foodrecipeapp.common

import com.project.foodrecipeapp.data.remote.dto.*
import com.project.foodrecipeapp.domain.model.*

fun MealListDTO.toMealListModel(): MealsList{
    return MealsList(
        meals = meals.map { it.toMealModel() }
    )
}

fun MealDTO.toMealModel(): Meal{
    return Meal(
        dateModified = dateModified,
        idMeal = idMeal,
        strArea = strArea,
        strCategory = strCategory,
        strCreativeCommonsConfirmed = strCreativeCommonsConfirmed,
        strDrinkAlternate = strDrinkAlternate,
        strImageSource = strImageSource,
        strIngredient1 = strIngredient1,
        strIngredient10 = strIngredient10,
        strIngredient11 = strIngredient11,
        strIngredient12 = strIngredient12,
        strIngredient13 = strIngredient13,
        strIngredient14 = strIngredient14,
        strIngredient15 = strIngredient15,
        strIngredient16 = strIngredient16,
        strIngredient17 = strIngredient17,
        strIngredient18 = strIngredient18,
        strIngredient19 = strIngredient19,
        strIngredient2 = strIngredient2,
        strIngredient20 = strIngredient20,
        strIngredient3 = strIngredient3,
        strIngredient4 = strIngredient4,
        strIngredient5 = strIngredient5,
        strIngredient6 = strIngredient6,
        strIngredient7 = strIngredient7,
        strIngredient8 = strIngredient8,
        strIngredient9 = strIngredient9,
        strInstructions = strInstructions,
        strMeal = strMeal,
        strMealThumb = strMealThumb,
        strMeasure1 = strMeasure1,
        strMeasure10 = strMeasure10,
        strMeasure11 = strMeasure11,
        strMeasure12 = strMeasure12,
        strMeasure13 = strMeasure13,
        strMeasure14 = strMeasure14,
        strMeasure15 = strMeasure15,
        strMeasure16 = strMeasure16,
        strMeasure17 = strMeasure17,
        strMeasure18 = strMeasure18,
        strMeasure19 = strMeasure19,
        strMeasure2 = strMeasure2,
        strMeasure20 = strMeasure20,
        strMeasure3 = strMeasure3,
        strMeasure4 = strMeasure4,
        strMeasure5 = strMeasure5,
        strMeasure6 = strMeasure6,
        strMeasure7 = strMeasure7,
        strMeasure8 = strMeasure8,
        strMeasure9 = strMeasure9,
        strSource = strSource,
        strTags = strTags,
        strYoutube = strYoutube
    )
}

fun AllMealDTO.toAllMealModel(): AllMeal {
    return AllMeal(
        meals = meals.map { it.toAllMealListModel() }
    )
}

fun AllMealListDTO.toAllMealListModel(): AllMealList {
    return AllMealList(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealThumb = strMealThumb
    )
}

fun CategoriesDTO.toCategoriesModel(): Categories{
    return Categories(
        categories = categories.map { it.toCategoryListModel() }
    )
}

fun CategoryListDTO.toCategoryListModel(): CategoryList{
    return CategoryList(
        idCategory = idCategory,
        strCategory = strCategory,
        strCategoryThumb = strCategoryThumb,
        strCategoryDescription = strCategoryDescription
    )
}

fun ExploreCategoriesDTO.toExploreCategoriesModel(): ExploreCategories{
    return ExploreCategories(
        meals = meals.map { it.toExploreCategoriesDetailModel() }
    )
}

fun ExploreCategoriesDetailDTO.toExploreCategoriesDetailModel(): ExploreCategoriesDetail{
    return ExploreCategoriesDetail(
        strCategory = strCategory
    )
}

fun ExploreAreaDTO.toExploreAreaModel(): ExploreArea{
    return ExploreArea(
        meals = meals.map { it.toExploreAreaDetailsModel() }
    )
}

fun ExploreAreaDetailsDTO.toExploreAreaDetailsModel(): ExploreAreaDetails{
    return ExploreAreaDetails(
        strArea = strArea
    )
}

fun ExploreIngredientsDTO.toExploreIngredientsModel(): ExploreIngredients{
    return ExploreIngredients(
        meals = meals.map { it.toExploreIngredientsDetailModel() }
    )
}

fun ExploreIngredientsDetailDTO.toExploreIngredientsDetailModel(): ExploreIngredientsDetail{
    return ExploreIngredientsDetail(
        idIngredient = idIngredient,
        strIngredient = strIngredient,
        strDescription = strDescription,
        strType = strType
    )
}