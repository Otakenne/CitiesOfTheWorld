package com.otakenne.citiesoftheworld.presentation.view_cities.view_model

import androidx.lifecycle.ViewModel
import com.otakenne.citiesoftheworld.domain.repository.CityRepository

class CityViewModel(
    private val cityRepository: CityRepository
): ViewModel() {

}