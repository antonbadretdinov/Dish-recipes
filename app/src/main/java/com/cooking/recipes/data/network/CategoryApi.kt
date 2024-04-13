package com.cooking.recipes.data.network

import com.cooking.recipes.data.models.CategoryListModel
import retrofit2.http.GET

interface CategoryApi {
    @GET("categories.php")
    suspend fun getAllCategories(): CategoryListModel
}