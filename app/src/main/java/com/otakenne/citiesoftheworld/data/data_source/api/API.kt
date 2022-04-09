package com.otakenne.citiesoftheworld.data.data_source.api

import com.otakenne.citiesoftheworld.domain.model.APIResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("")
    suspend fun getCities(
        @Query("page") page: String = "1",
        @Query("include") include: String = "country",
        @Query("filter") filter: String = ""
    ): APIResponse
}