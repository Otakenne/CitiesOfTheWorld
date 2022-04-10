package com.otakenne.citiesoftheworld.presentation.view_cities.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.databinding.CityListFooterBinding

class CityLoadStateViewHolder(
    private val binding: CityListFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root){

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    /**
     * Set the visibility of each view depending on the load state
     * @author Otakenne
     */
    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CityLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.city_list_footer, parent, false)
            val binding = CityListFooterBinding.bind(view)
            return CityLoadStateViewHolder(binding, retry)
        }
    }
}