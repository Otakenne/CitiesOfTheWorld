package com.otakenne.citiesoftheworld.data.data_source.api

import com.otakenne.citiesoftheworld.domain.model.APIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface responsible for calls to the Square1's City API
 * @author Otakenne
 */
interface API {
    /**
     * Returns all the cities related to search term (filter) from the Square1 backend
     * @param page: integer to control pagination
     * @param include: flag to include the country class/object as part of the API response
     * @param filter: search term
     * @return RxJava single shot of the GithubUserResponse model
     * @author Otakenne
     */
    @GET(".")
    suspend fun getCities(
        @Query("page") page: String = "1",
        @Query("include") include: String = "country",
        @Query("filter[0][name][contains]") filter: String = ""
    ): APIResponse
}