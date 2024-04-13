package com.cooking.recipes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.recipes.data.models.MealsPreviewListModel
import com.cooking.recipes.data.repositories.MealsPreviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsPreviewViewModel @Inject constructor(
    private val mealsPreviewRepository: MealsPreviewRepository
) : ViewModel() {

    private val mutableMealsPreviewFlow = MutableStateFlow(MealsPreviewListModel(emptyList()))
    val mealsPreviewFlow: StateFlow<MealsPreviewListModel> = mutableMealsPreviewFlow

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
    }

    fun getMealsPreviewByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            mutableMealsPreviewFlow.value = mealsPreviewRepository.getMealsByCategory(category = category)
        }
    }

    fun getMealsPreviewByArea(area: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            mutableMealsPreviewFlow.value = mealsPreviewRepository.getMealsByArea(area = area)
        }
    }
}