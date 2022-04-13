package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.otakenne.citiesoftheworld.domain.model.RemoteKeys

@Dao
interface RemoteKeysDao {
    /**
     * Insert a list of remotekeys into room for the remotemediator
     * @author Otakenne
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>)

    /**
     * Get remotekey object by cityID
     * @return remotekey object
     * @author Otakenne
     */
    @Query("SELECT * FROM remote_keys_table WHERE cityID = :cityID")
    suspend fun getRemoteKeysFrom(cityID: Long): RemoteKeys

    /**
     * Delete all remote keys from room
     * @author Otakenne
     */
    @Query("DELETE FROM remote_keys_table")
    suspend fun clearRemoteKeys()
}