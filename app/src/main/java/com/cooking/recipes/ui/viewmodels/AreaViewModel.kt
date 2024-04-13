package com.cooking.recipes.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.recipes.data.models.AreaListModel
import com.cooking.recipes.data.repositories.AreaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AreaViewModel @Inject constructor(
    private val areaRepository: AreaRepository
) : ViewModel() {

    private val mutableAreaFlow = MutableStateFlow(AreaListModel(emptyList()))
    val areaFlow: StateFlow<AreaListModel> = mutableAreaFlow

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println(throwable.message)
    }

    fun getAllAreas() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            mutableAreaFlow.value = areaRepository.getAllAreas()
        }
    }
}