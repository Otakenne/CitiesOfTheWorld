package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otakenne.citiesoftheworld.domain.model.City

/**
 * Data access object to perform bulk operations on the github_user_table (get and delete all users)
 * @author Otakenne
 */
@Dao
interface CityDao {
    /**
     * Insert a list of cities into room
     * @author Otakenne
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<City>)

    /**
     * Delete all persisted cities
     * @author Otakenne
     */
    @Query("DELETE from city_table")
    suspend fun deleteCities()

    /**
     * Return all cities from room as a LiveData object
     * @return LiveData of list of cities
     * @author Otakenne
     */
    @Query("SELECT * from city_table")
    fun getCitiesByLD(): LiveData<List<City>>

    /**
     * Get cities filtered by search term from room
     * @return A PagingSource object
     * @author Otakenne
     */
    @Query("SELECT * from city_table WHERE name LIKE :searchTerm")
    fun getCitiesBy(searchTerm : String): PagingSource<Int, City>
}