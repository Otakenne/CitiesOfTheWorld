package com.otakenne.citiesoftheworld.presentation.view_cities.viewholders

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.databinding.CityRowBinding
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.utility.Math

class CityViewHolder(private val binding: CityRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(city: City) {
        binding.cityName.text = city.name
        binding.countryName.text = city.country?.name ?: "na"

        if (city.latitude != null) {
            binding.latitude.text = binding.latitude.context.getString(
                R.string.latitude,
                Math.roundCoordinate(city.latitude, 5)
            )
        } else {
            binding.latitude.text = binding.latitude.context.getString(
                R.string.latitude,
                "0.0"
            )
        }

        if (city.longitude != null) {
            binding.longitude.text = binding.latitude.context.getString(
                R.string.longitude,
                Math.roundCoordinate(city.longitude, 5)
            )
        } else {
            binding.longitude.text = binding.latitude.context.getString(
                R.string.longitude,
                "0.0"
            )
        }
    }

    companion object {
        fun create(parent: ViewGroup): CityViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.city_row,  parent,false)
            val binding = CityRowBinding.bind(view)
            return CityViewHolder( binding )
        }
    }
}