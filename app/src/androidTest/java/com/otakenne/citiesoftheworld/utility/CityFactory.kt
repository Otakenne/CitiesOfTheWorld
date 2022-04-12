package com.otakenne.citiesoftheworld.utility

import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.model.Country
import java.util.concurrent.atomic.AtomicInteger

class CityFactory {
    private val startPoint = 0
    private val counter = AtomicInteger(startPoint)
    fun createCity(): City {
        val id = counter.incrementAndGet()

        return City(
            id = id.toLong(),
            name = "City $id",
            localName = "Local City $id",
            latitude = 0.0,
            longitude = 0.0,
            createdAt = DateConverter.makeDateReadable(System.currentTimeMillis()),
            updatedAt = DateConverter.makeDateReadable(System.currentTimeMillis()),
            countryId = id,
            country = Country(
                id = id,
                name = "Country $id",
                code = "CNTRY$id",
                createdAt = DateConverter.makeDateReadable(System.currentTimeMillis()),
                updatedAt = DateConverter.makeDateReadable(System.currentTimeMillis()),
                continentId = id
            )
        )
    }
}