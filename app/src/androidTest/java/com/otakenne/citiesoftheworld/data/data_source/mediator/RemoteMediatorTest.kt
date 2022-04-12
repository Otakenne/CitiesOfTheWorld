package com.otakenne.citiesoftheworld.data.data_source.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.otakenne.citiesoftheworld.data.data_source.database.Database
import com.otakenne.citiesoftheworld.data.data_source.utility.Constants
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.utility.CityFactory
import com.otakenne.citiesoftheworld.utility.MockAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RemoteMediatorTest {
    private lateinit var cityFactory : CityFactory
    private lateinit var mockCities: List<City>

    private lateinit var mockAPI: MockAPI
    private lateinit var mockDB: Database

    @Before
    fun setup() {
        cityFactory = CityFactory()
        mockAPI = MockAPI()

        mockCities = listOf(
            cityFactory.createCity(),
            cityFactory.createCity(),
            cityFactory.createCity(),
            cityFactory.createCity(),
        )

        mockDB = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown() {
        mockAPI.failureMessage = null
        mockAPI.clear()
        mockDB.clearAllTables()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        mockCities.forEach {
            mockAPI.addCity(it)
        }
        val remoteMediator = RemoteMediator(Constants.FILTER, mockDB, mockAPI)
        val pagingState = PagingState<Int, City>(listOf(), null, PagingConfig(15), 15)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is androidx.paging.RemoteMediator.MediatorResult.Success)
        assertFalse((result as androidx.paging.RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        val remoteMediator = RemoteMediator(Constants.FILTER, mockDB, mockAPI)
        val pagingState = PagingState<Int, City>(listOf(), null, PagingConfig(15), 15)

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is androidx.paging.RemoteMediator.MediatorResult.Success)
        assertTrue((result as androidx.paging.RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        mockAPI.failureMessage = "Throw test failure"
        val remoteMediator = RemoteMediator(Constants.FILTER, mockDB, mockAPI)
        val pagingState = PagingState<Int, City>(listOf(), null, PagingConfig(15), 15)
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is androidx.paging.RemoteMediator.MediatorResult.Error)
    }
}