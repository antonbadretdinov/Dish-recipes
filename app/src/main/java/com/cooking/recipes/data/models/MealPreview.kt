package com.cooking.recipes.data.models

import androidx.annotation.Keep
import androidx.compose.runtime.Stable

@Keep
@Stable
data class MealsPreviewListModel(
    val meals: List<MealPreview>
)

@Keep
@Stable
data class MealPreview(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

