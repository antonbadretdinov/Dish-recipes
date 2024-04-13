package com.cooking.recipes.di

import com.cooking.recipes.data.network.AreaApi
import com.cooking.recipes.data.network.CategoryApi
import com.cooking.recipes.data.network.MealPreviewApi
import com.cooking.recipes.data.network.RecipeApi
import com.cooking.recipes.helpers.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideCategoryService(retrofit: Retrofit): CategoryApi =
        retrofit.create(CategoryApi::class.java)

    @Singleton
    @Provides
    fun provideAreaService(retrofit: Retrofit): AreaApi =
        retrofit.create(AreaApi::class.java)

    @Singleton
    @Provides
    fun provideMealPreviewService(retrofit: Retrofit): MealPreviewApi =
        retrofit.create(MealPreviewApi::class.java)

    @Singleton
    @Provides
    fun provideRecipeService(retrofit: Retrofit): RecipeApi =
        retrofit.create(RecipeApi::class.java)
}