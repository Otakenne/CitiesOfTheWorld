package com.otakenne.citiesoftheworld.presentation.view_cities.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.databinding.CityRowBinding
import com.otakenne.citiesoftheworld.domain.model.City

class CityViewHolder(private val binding: CityRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(city: City) {
        binding.cityName.text = city.name
        binding.countryName.text = city.country?.name ?: "na"
        binding.latitude.text = "Latitude: ${city.latitude.toString()}"
        binding.longitude.text = "Longitude: ${city.longitude.toString()}"
    }

    companion object {
        fun create(parent: ViewGroup): CityViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.city_row,  parent,false)
            val binding = CityRowBinding.bind(view)
            return CityViewHolder( binding )
        }
    }
}