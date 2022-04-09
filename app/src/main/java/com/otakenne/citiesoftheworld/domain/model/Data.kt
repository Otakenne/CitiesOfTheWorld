package com.otakenne.citiesoftheworld.domain.model

data class Data(
    val items: List<City> = listOf(),
    val pagination: Pagination
)
