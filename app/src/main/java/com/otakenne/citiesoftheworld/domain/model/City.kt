package com.otakenne.citiesoftheworld.domain.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city_table")
data class City (
    @PrimaryKey
    @NonNull
    val id: Long,
    val name: String,
    @SerializedName("local_name") val localName: String,
    @SerializedName("lat") val latitude: String,
    @SerializedName("lng") val longitude: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("country_id") val countryId: Int,
//    val country: Country? = null
)