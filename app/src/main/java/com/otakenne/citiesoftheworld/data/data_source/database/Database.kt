package com.otakenne.citiesoftheworld.data.data_source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.model.RemoteKeys

@Database(
    entities = [City::class, RemoteKeys::class],
    version = 1
)
abstract class Database: RoomDatabase() {

    abstract fun citiesDao(): CityDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DATABASE_NAME = "cities_db"
    }
}