package com.cooking.recipes.data.repositories

import com.cooking.recipes.data.network.RecipeApi
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val recipeService: RecipeApi
) {
    suspend fun getRecipeByMealId(id: String) = recipeService.getRecipeByMealId(id)
}