package com.otakenne.citiesoftheworld.domain.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("last_page") val lastPage: Int,
    @SerializedName("per_page") val perPage: Int,
    val total: Int
)
