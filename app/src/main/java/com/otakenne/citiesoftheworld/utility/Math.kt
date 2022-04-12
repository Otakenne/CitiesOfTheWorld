package com.otakenne.citiesoftheworld.utility

class Math {
    companion object {
        fun roundCoordinate(coordinate: Double, numberOfDecimals: Int): String {
            val stringFormat = "%.${numberOfDecimals}f"
            return String.format(stringFormat, coordinate)
        }
    }
}