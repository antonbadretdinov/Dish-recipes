package com.cooking.recipes.data.network

import com.cooking.recipes.data.models.AreaListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaApi {

    @GET("list.php")
    suspend fun getAllAreas(
        @Query("a") list: String = "list"
    ): AreaListModel

}