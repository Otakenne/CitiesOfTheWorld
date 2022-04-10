package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.room.TypeConverter
import com.otakenne.citiesoftheworld.domain.model.Country
import org.json.JSONObject

class CountryTypeConverter {
    @TypeConverter
    fun fromCountry(country: Country): String {
        return JSONObject().apply {
            put("id", country.id)
            put("name", country.name)
            put("code", country.code)
            put("createdAt", country.createdAt)
            put("updatedAt", country.updatedAt)
            put("continentId", country.continentId)
        }.toString()
    }

    @TypeConverter
    fun toCountry(country: String): Country {
        val json = JSONObject(country)
        return Country(
            json.getInt("id"),
            json.getString("name"),
            json.getString("code"),
            json.getString("createdAt"),
            json.getString("updatedAt"),
            json.getInt("continentId")
        )
    }
}