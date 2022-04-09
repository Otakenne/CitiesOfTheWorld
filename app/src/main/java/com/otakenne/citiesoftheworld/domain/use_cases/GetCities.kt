package com.otakenne.citiesoftheworld.domain.use_cases

import androidx.paging.PagingData
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow

class GetCities (
    private val cityRepository: CityRepository
) {
    operator fun invoke(query: String): Flow<PagingData<City>> {
        return cityRepository.getSearchResultStream(query)
    }
}