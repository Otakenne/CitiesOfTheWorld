package com.otakenne.citiesoftheworld.domain.model

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class Country (
    @NonNull
    val id: Int,
    val name: String,
    val code: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("continent_id") val continentId: Int
)