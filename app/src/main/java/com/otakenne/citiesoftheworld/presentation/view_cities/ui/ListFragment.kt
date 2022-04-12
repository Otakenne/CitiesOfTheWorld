package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.databinding.FragmentListBinding
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.presentation.view_cities.adapters.CityAdapter
import com.otakenne.citiesoftheworld.presentation.view_cities.adapters.CityLoadStateAdapter
import com.otakenne.citiesoftheworld.presentation.view_cities.view_model.CityViewModel
import com.otakenne.citiesoftheworld.presentation.view_cities.view_model.UiAction
import com.otakenne.citiesoftheworld.presentation.view_cities.view_model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var _binding: FragmentListBinding
    private val binding get() = _binding

    private val viewModel: CityViewModel by activityViewModels()
    @Inject lateinit var adapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is HomeActivity) {
            (activity as HomeActivity).supportActionBar?.title = "Cities of the World"
        }

        binding.switchToMapView.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToMapFragment()
            findNavController().navigate(action)
        }

        bindState(
            viewModel.state,
            viewModel.pagedCityFlow,
            viewModel.action
        )
    }

    private fun bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<City>>,
        uiActions: (UiAction) -> Unit
    ) {
        binding.cityList.adapter = adapter.withLoadStateHeaderAndFooter(
            header = CityLoadStateAdapter( retry = { adapter.retry() }),
            footer = CityLoadStateAdapter( retry = { adapter.retry() })
        )

        bindSearch(uiState, uiActions)

        bindList(
            adapter = adapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        binding.search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        binding.search.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(binding.search::setText)
        }
    }

    private fun updateListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        binding.search.text.trim().let {
            if (it.isNotEmpty()) {
                binding.cityList.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun bindList(
        adapter: CityAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<City>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        binding.retryButton.setOnClickListener { adapter.retry() }
        binding.cityList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) {
                    onScrollChanged(UiAction.Scroll(uiState.value.query))
                }
            }
        })

        val notLoading = adapter
            .loadStateFlow
            .distinctUntilChangedBy { combinedLoadStates ->
                combinedLoadStates.source.refresh
            }.map { combinedLoadStates ->
                combinedLoadStates.source.refresh is LoadState.NotLoading
            }

        val hasNotScrolledForCurrentSearch = uiState
            .map { uiState ->
                uiState.hasNotScrolledForCurrentSearch
            }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        ).distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(adapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) binding.cityList.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val isListEmpty = adapter.itemCount == 0

                binding.noResultsText.isVisible = isListEmpty
                binding.cityList.isVisible = !isListEmpty
                binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            }
        }
    }
}