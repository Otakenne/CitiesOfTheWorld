package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.model.Country
import com.otakenne.citiesoftheworld.getOrAwaitValue
import com.otakenne.citiesoftheworld.utility.CityFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class CityDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: Database
    private lateinit var dao: CityDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            Database::class.java
        ).allowMainThreadQueries().build()
        dao = database.citiesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCitiesTest() = runBlocking {
        val numberOfEntries = 5
        val cities = mutableListOf<City>()
        val cityFactory = CityFactory()

        for (i in 1..numberOfEntries) {
            cities.add(cityFactory.createCity())
        }

        dao.insertCities(cities)
        val citiesFromDB = dao.getCitiesByLD().getOrAwaitValue()
        assertThat(citiesFromDB.size).isEqualTo(numberOfEntries)
    }

    @Test
    fun deleteAllCitiesTest() = runBlocking {
        val numberOfEntries = 5
        val cities = mutableListOf<City>()
        val cityFactory = CityFactory()

        for (i in 1..numberOfEntries) {
            cities.add(cityFactory.createCity())
        }

        dao.insertCities(cities)
        dao.deleteCities()
        val citiesFromDB = dao.getCitiesByLD().getOrAwaitValue()
        assertThat(citiesFromDB.size).isEqualTo(0)
    }
}