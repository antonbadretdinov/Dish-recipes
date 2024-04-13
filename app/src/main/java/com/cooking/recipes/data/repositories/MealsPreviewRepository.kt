package com.cooking.recipes.data.repositories

import com.cooking.recipes.data.network.MealPreviewApi
import javax.inject.Inject

class MealsPreviewRepository @Inject constructor(
    private val mealsPreviewService: MealPreviewApi
) {
    suspend fun getMealsByCategory(category: String) = mealsPreviewService.getMealsByCategory(category)

    suspend fun getMealsByArea(area: String) = mealsPreviewService.getMealsByArea(area)
}