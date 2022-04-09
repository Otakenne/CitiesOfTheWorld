package com.otakenne.citiesoftheworld.di

import com.otakenne.citiesoftheworld.domain.repository.CityRepository
import com.otakenne.citiesoftheworld.domain.use_cases.GetCities
import com.otakenne.citiesoftheworld.domain.use_cases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providesUseCases(cityRepository: CityRepository): UseCases {
        return UseCases(GetCities(cityRepository))
    }
}