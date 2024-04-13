package com.cooking.recipes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.recipes.data.models.CategoryListModel
import com.cooking.recipes.data.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val mutableCategoriesFlow = MutableStateFlow(CategoryListModel(emptyList()))
    val categoriesFlow: StateFlow<CategoryListModel> = mutableCategoriesFlow

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
    }

    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            mutableCategoriesFlow.value = categoryRepository.getAllCategories()
        }
    }
}