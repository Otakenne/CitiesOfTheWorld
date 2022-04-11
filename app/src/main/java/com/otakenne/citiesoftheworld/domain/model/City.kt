package com.otakenne.citiesoftheworld.domain.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "city_table")
data class City (
    @PrimaryKey
    @NonNull
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("local_name") val localName: String?,
    @SerializedName("lat") val latitude: Double?,
    @SerializedName("lng") val longitude: Double?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("country_id") val countryId: Int?,
    @SerializedName("country") val country: Country? = null
)