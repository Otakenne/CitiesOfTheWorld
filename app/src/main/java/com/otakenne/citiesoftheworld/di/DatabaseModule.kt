package com.otakenne.citiesoftheworld.di

import android.app.Application
import androidx.room.Room
import com.otakenne.citiesoftheworld.data.data_source.api.API
import com.otakenne.citiesoftheworld.data.data_source.database.CityDao
import com.otakenne.citiesoftheworld.data.data_source.database.Database
import com.otakenne.citiesoftheworld.data.data_source.database.RemoteKeysDao
import com.otakenne.citiesoftheworld.data.repository.CityRepositoryImplementation
import com.otakenne.citiesoftheworld.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesCityDao(database: Database): CityDao {
        return database.citiesDao()
    }

    @Provides
    @Singleton
    fun providesRemoteKeysDao(database: Database): RemoteKeysDao {
        return database.remoteKeysDao()
    }

    @Provides
    @Singleton
    fun providesCityRepository(
        database: Database,
        api: API
    ): CityRepository {
        return CityRepositoryImplementation(database, api)
    }

    @Provides
    @Singleton
    fun providesDatabase(application: Application): Database {
        return Room.databaseBuilder(
            application,
            Database::class.java,
            Database.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}