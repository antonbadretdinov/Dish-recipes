package com.cooking.recipes.data.repositories

import com.cooking.recipes.data.network.AreaApi
import javax.inject.Inject

class AreaRepository @Inject constructor(
    private val areaService: AreaApi
) {
    suspend fun getAllAreas() = areaService.getAllAreas()
}