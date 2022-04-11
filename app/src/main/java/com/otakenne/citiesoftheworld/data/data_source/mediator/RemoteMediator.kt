package com.otakenne.citiesoftheworld.data.data_source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.otakenne.citiesoftheworld.data.data_source.api.API
import com.otakenne.citiesoftheworld.data.data_source.database.Database
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.model.RemoteKeys
import com.otakenne.citiesoftheworld.utility.Constants
import retrofit2.HttpException
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RemoteMediator(
    private val query: String,
    private val database: Database,
    private val api: API
): RemoteMediator<Int, City>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, City>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val previousKey = remoteKeys?.previousKey
                if (previousKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                previousKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }
        val apiQuery = query

        try {
            val apiResponse = api.getCities(
                page = page.toString(),
                include = "country",
                filter = apiQuery
            )//.awaitResponse().raw().request.url

            val cities = apiResponse.data.items
            val endOfPaginationReached = cities.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.citiesDao().deleteCities()
                }
                val previousKeys = if (page == Constants.STARTING_PAGE_INDEX) null else page - 1
                val nextKeys = if (endOfPaginationReached) null else page + 1
                val keys = cities.map {
                    RemoteKeys(cityID = it.id, previousKey = previousKeys, nextKey = nextKeys)
                }
                database.remoteKeysDao().insertRemoteKeys(keys)
                database.citiesDao().insertCities(cities = cities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, City>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { cityId ->
                database.remoteKeysDao().getRemoteKeysFrom(cityId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, City>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { city ->
                database.remoteKeysDao().getRemoteKeysFrom(city.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, City>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { city ->
                database.remoteKeysDao().getRemoteKeysFrom(city.id)
            }
    }
}