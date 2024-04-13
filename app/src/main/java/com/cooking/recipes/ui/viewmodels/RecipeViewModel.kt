package com.cooking.recipes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.recipes.data.models.RecipeListModel
import com.cooking.recipes.data.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val mutableRecipeFlow = MutableStateFlow(RecipeListModel(emptyList()))
    val recipeFlow: StateFlow<RecipeListModel> = mutableRecipeFlow

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
    }

    fun getMealsRecipeById(id: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            mutableRecipeFlow.value = recipeRepository.getRecipeByMealId(id)
        }
    }
}