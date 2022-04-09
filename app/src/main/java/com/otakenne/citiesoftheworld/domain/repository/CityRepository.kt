package com.otakenne.citiesoftheworld.domain.repository

import androidx.paging.PagingData
import com.otakenne.citiesoftheworld.domain.model.City
import kotlinx.coroutines.flow.Flow

interface CityRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<City>>
}