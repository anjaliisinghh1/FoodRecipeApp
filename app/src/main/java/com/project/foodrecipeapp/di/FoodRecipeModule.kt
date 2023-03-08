package com.project.foodrecipeapp.di

import com.project.foodrecipeapp.common.Constants.BASE_URL
import com.project.foodrecipeapp.data.remote.FoodRecipeApi
import com.project.foodrecipeapp.data.repositoryImpl.FoodRecipeRepositoryImpl
import com.project.foodrecipeapp.domain.repository.FoodRecipeRepository
import com.project.foodrecipeapp.domain.usecases.GetFoodRecipeDetailByIdUseCase
import com.project.foodrecipeapp.domain.usecases.GetRandomMealUseCase
import com.project.foodrecipeapp.presentation.viewModel.FoodRecipeDetailViewModel
import com.project.foodrecipeapp.presentation.viewModel.HomeFoodViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
     viewModel { HomeFoodViewModel(get()) }
     viewModel { FoodRecipeDetailViewModel(get()) }
}

val usecaseModule: Module = module {
    single { GetRandomMealUseCase(get()) }
    single { GetFoodRecipeDetailByIdUseCase(get()) }
}