package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otakenne.citiesoftheworld.domain.model.City

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<City>)

    @Query("DELETE from city_table")
    suspend fun deleteCities()

    @Query("SELECT * from city_table")
    fun getCitiesByLD(): LiveData<List<City>>

    @Query("SELECT * from city_table WHERE name LIKE :searchTerm")
    fun getCitiesBy(searchTerm : String): PagingSource<Int, City>
}