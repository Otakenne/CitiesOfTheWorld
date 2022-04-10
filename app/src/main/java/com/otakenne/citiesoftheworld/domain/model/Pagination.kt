package com.otakenne.citiesoftheworld.domain.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("current_page") val currentPage: Int = 2,
    @SerializedName("last_page") val lastPage: Int = 272,
    @SerializedName("per_page") val perPage: Int = 15,
    @SerializedName("total") val total: Int = 0
)
