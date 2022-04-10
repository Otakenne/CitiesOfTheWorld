package com.otakenne.citiesoftheworld.presentation.view_cities.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.otakenne.citiesoftheworld.domain.model.City
import com.otakenne.citiesoftheworld.domain.use_cases.UseCases
import com.otakenne.citiesoftheworld.utility.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val state: StateFlow<UiState>
    val pagedCityFlow: Flow<PagingData<City>>
    val action: (UiAction) -> Unit

    init {
        val actionStateFlow = MutableSharedFlow<UiAction>()
        val initialQuery: String = savedStateHandle.get(Constants.LAST_SEARCH_QUERY) ?: ""
        val lastQueryScrolled: String = savedStateHandle.get(Constants.LAST_QUERY_SCROLLED) ?: ""

        val filter = actionStateFlow
            .filterIsInstance<UiAction.Search>()
            .distinctUntilChanged()
            .onStart {
                emit(
                    UiAction.Search(initialQuery)
                )
            }

        val scrolls = actionStateFlow
            .filterIsInstance<UiAction.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(3000),
                1
            ).onStart {
                emit(
                    UiAction.Scroll(lastQueryScrolled)
                )
            }


        action = { action ->
            viewModelScope.launch { actionStateFlow.emit(action) }
        }

        pagedCityFlow = filter
            .flatMapLatest { uiActionSearch ->
                searchCity(uiActionSearch.query)
            }.cachedIn(viewModelScope)

        state = combine(
            filter,
            scrolls,
            ::Pair
        ).map { (filter, scroll) ->
            UiState(
                filter.query,
                scroll.currentQuery,
                filter.query != scroll.currentQuery
            )
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(3000),
            UiState()
        )
    }

    private fun searchCity(queryString: String): Flow<PagingData<City>> {
        return useCases.getCities.invoke(queryString)
    }

//    override fun onCleared() {
//        savedStateHandle[Constants.LAST_SEARCH_QUERY] = state.value.query
//        savedStateHandle[Constants.LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
//        super.onCleared()
//    }
}

data class UiState (
    val query: String = "ka",
    val lastQueryScrolled: String = "ka",
    val hasNotScrolledForCurrentSearch: Boolean = false
)

sealed class UiAction {
    data class Search(val query: String) : UiAction()
    data class Scroll(val currentQuery: String) : UiAction()
}