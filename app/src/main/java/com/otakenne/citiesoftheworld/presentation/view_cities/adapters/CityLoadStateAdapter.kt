package com.otakenne.citiesoftheworld.presentation.view_cities.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.otakenne.citiesoftheworld.presentation.view_cities.viewholders.CityLoadStateViewHolder

class CityLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<CityLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CityLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CityLoadStateViewHolder {
        return CityLoadStateViewHolder.create(parent, retry)
    }
}