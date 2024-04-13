package com.cooking.recipes.data.network

import com.cooking.recipes.data.models.MealsPreviewListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MealPreviewApi {

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") category: String,
    ): MealsPreviewListModel

    @GET("filter.php")
    suspend fun getMealsByArea(
        @Query("a") area: String
    ): MealsPreviewListModel

}