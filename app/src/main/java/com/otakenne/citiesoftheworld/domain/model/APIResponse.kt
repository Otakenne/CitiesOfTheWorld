package com.otakenne.citiesoftheworld.domain.model

import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("data") val data: Data,
    @SerializedName("time") val time: Long
)