package com.otakenne.citiesoftheworld.domain.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("items") val items: List<City> = emptyList(),
    @SerializedName("pagination") val pagination: Pagination? = null
)
