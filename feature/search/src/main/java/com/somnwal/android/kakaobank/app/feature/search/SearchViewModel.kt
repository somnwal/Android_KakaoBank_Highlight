package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somnwal.android.kakaobank.app.core.domain.usecase.GetSearchResultWithFavoriteUseCase
import com.somnwal.android.kakaobank.app.core.domain.usecase.UpdateIsFavoriteUseCase
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCH_QUERY = "SEARCH_QUERY"
const val SEARCH_PAGE = "SEARCH_PAGE"
const val SHOULD_REFRESH = "SHOULD_REFRESH"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultWithFavoriteUseCase: GetSearchResultWithFavoriteUseCase,
    private val updateIsFavoriteUseCase: UpdateIsFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    var searchPage = savedStateHandle.getStateFlow(key = SEARCH_PAGE, initialValue = 1)
    var shouldRefresh = savedStateHandle.getStateFlow(key = SHOULD_REFRESH, initialValue = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    var uiState : StateFlow<SearchUiState> =
        shouldRefresh.flatMapLatest { _ ->
            searchQuery.flatMapLatest { query ->
                searchPage.flatMapLatest { page ->
                    getSearchResultWithFavoriteUseCase(query, page)
                        .map<List<SearchData>, SearchUiState> { data ->
                            SearchUiState.Success(
                                data = data
                            )
                        }.catch { err ->
                            _errorState.value = err
                        }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState.Idle
        )

    private var _errorState = MutableStateFlow<Throwable?>(null)
    var errorState : StateFlow<Throwable?> = _errorState.asStateFlow()

    fun onSearch(query: String, page: Int = 1) {
        savedStateHandle[SEARCH_QUERY] = query
        savedStateHandle[SEARCH_PAGE] = page
    }

    fun onNextPage() {
        savedStateHandle[SEARCH_PAGE] = searchPage.value + 1
    }

    fun updateIsFavorite(searchData: SearchData) {
        viewModelScope.launch {
            updateIsFavoriteUseCase(searchData, !searchData.isFavorite)
            savedStateHandle[SHOULD_REFRESH] = shouldRefresh.value + 1
        }
    }
}