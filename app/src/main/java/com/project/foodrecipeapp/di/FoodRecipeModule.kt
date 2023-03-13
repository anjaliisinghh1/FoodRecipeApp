package com.project.foodrecipeapp.di

import com.project.foodrecipeapp.common.Constants.BASE_URL
import com.project.foodrecipeapp.data.remote.FoodRecipeApi
import com.project.foodrecipeapp.data.repositoryImpl.FoodRecipeRepositoryImpl
import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository
import com.project.foodrecipeapp.domain.usecases.*
import com.project.foodrecipeapp.presentation.viewModel.FoodRecipeDetailViewModel
import com.project.foodrecipeapp.presentation.viewModel.HomeFoodExploreViewModel
import com.project.foodrecipeapp.presentation.viewModel.HomeFoodViewModel
import com.project.foodrecipeapp.presentation.viewModel.MealListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

val apiModule: Module = module {

    fun foodRecipeApi(retrofit: Retrofit): FoodRecipeApi {
        return retrofit.create(FoodRecipeApi::class.java)
    }

    single { foodRecipeApi(get()) }
}

val retrofitModule: Module = module {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { provideRetrofit() }
}

val repositoryModule: Module = module {

    single<FoodRecipeRepository> {
        FoodRecipeRepositoryImpl(get())
    }
}

val viewmodelModule: Module = module {
    viewModel { HomeFoodViewModel(get(), get(), get()) }
    viewModel { FoodRecipeDetailViewModel(get()) }
    viewModel { MealListViewModel(get(),get(),get()) }
    viewModel { HomeFoodExploreViewModel(get(), get(), get()) }
}

val usecaseModule: Module = module {
    single { GetRandomMealUseCase(get()) }
    single { GetFoodRecipeDetailByIdUseCase(get()) }
    single { GetFoodByCategoryUseCase(get()) }
    single { GetAllCategoriesUseCase(get()) }
    single { GetAllExploreCategoriesUseCase(get()) }
    single { GetAllExploreAreaUseCase(get()) }
    single { GetAllExploreIngredientsUseCase(get()) }
    single { GetFoodByAreaUseCase(get()) }
    single { GetFoodByIngredientUseCase(get()) }
}