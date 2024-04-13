package com.cooking.recipes.data.models

import androidx.annotation.Keep
import androidx.compose.runtime.Stable

@Keep
@Stable
data class CategoryListModel(
    val categories: List<CategoryModel>
)

@Keep
@Stable
data class CategoryModel(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)