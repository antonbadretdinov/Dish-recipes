package com.cooking.recipes.data.network

import com.cooking.recipes.data.models.RecipeListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("lookup.php")
    suspend fun getRecipeByMealId(
        @Query("i") id: String
    ): RecipeListModel

}