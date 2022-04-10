package com.otakenne.citiesoftheworld.presentation.view_cities.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.presentation.view_cities.viewholders.CityViewHolder
import javax.inject.Inject

class CityAdapter @Inject constructor(): PagingDataAdapter<City, CityViewHolder>(COMPARATOR) {
    /**
     * Documentation provided by Android
     */
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        getItem(position)?.let {
            val githubUser = it
            holder.bind(githubUser)
        }
    }

    /**
     * Documentation provided by Android
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder.create( parent )
    }

    /**
     * Compares changes in the paging adapter and reacts by updating the view
     * @author Otakenne
     */
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<City>() {
            override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
                return oldItem == newItem
            }
        }
    }
}