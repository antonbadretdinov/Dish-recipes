package com.cooking.recipes.data.repositories

import com.cooking.recipes.data.network.CategoryApi
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryService: CategoryApi) {
    suspend fun getAllCategories() = categoryService.getAllCategories()
}