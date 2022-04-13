package com.otakenne.citiesoftheworld.utility

import com.otakenne.citiesoftheworld.data.data_source.api.API
import com.otakenne.citiesoftheworld.domain.model.APIResponse
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.model.Data
import retrofit2.http.Query
import java.io.IOException

class MockAPI: API {
    private val model = mutableListOf<City >()
    var failureMessage: String? = null

    fun addCity(city: City) {
        model.add(city)
    }

    fun clear() {
        model.clear()
    }

    override suspend fun getCities(
        @Query("page") page: String,
        @Query("include") include: String,
        @Query("filter[0][name][contains]") filter: String
    ): APIResponse {
        failureMessage?.let {
            throw IOException(it)
        }

        val data = Data(model.filter { it.name!!.contains(filter) } )
        return APIResponse(data, System.currentTimeMillis())
    }
}