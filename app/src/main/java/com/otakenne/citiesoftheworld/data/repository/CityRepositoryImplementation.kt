package com.otakenne.citiesoftheworld.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.otakenne.citiesoftheworld.data.data_source.api.API
import com.otakenne.citiesoftheworld.data.data_source.database.Database
import com.otakenne.citiesoftheworld.data.data_source.mediator.RemoteMediator
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.repository.CityRepository
import com.otakenne.citiesoftheworld.utility.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImplementation @Inject constructor(
    private val database: Database,
    private val api: API
): CityRepository {
    override fun getSearchResultStream(query: String): Flow<PagingData<City>> {
        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingSourceFactory = { database.citiesDao().getCitiesBy(searchTerm = dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager (
            config = PagingConfig (
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RemoteMediator (
                query,
                database,
                api
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}