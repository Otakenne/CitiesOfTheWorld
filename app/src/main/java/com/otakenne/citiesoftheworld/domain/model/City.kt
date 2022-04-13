package com.otakenne.citiesoftheworld.domain.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem

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
): ClusterItem {
    override fun getPosition(): LatLng = LatLng((latitude ?: 0.0), ((longitude ?: 0.0)))

    override fun getTitle(): String = name ?: "City X"

    override fun getSnippet(): String = "$name in ${country?.name}"
}