package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.somnwal.android.kakaobank.app.core.domain.usecase.GetSearchResultUseCase
import com.somnwal.android.kakaobank.app.core.domain.usecase.GetSearchResultWithFavoriteUseCase
import com.somnwal.android.kakaobank.app.core.domain.usecase.UpdateIsFavoriteUseCase
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultWithFavoriteUseCase: GetSearchResultWithFavoriteUseCase,
    private val updateIsFavoriteUseCase: UpdateIsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private val _error = MutableSharedFlow<Throwable>()

    val error
        get() = _error.asSharedFlow()

    private val _loading = MutableStateFlow(false)

    val loading
        get() = _loading.asStateFlow()

    fun search(
        query: String,
        sort: String = "recency",
        page: Int
    ) {
        viewModelScope.launch {
            _loading.value = true

            getSearchResultWithFavoriteUseCase(query, sort, page)
                .collectLatest { searchResult ->
                    _uiState.value = SearchUiState.Success(
                        isNextPageExist = searchResult.isNextPageExist,
                        data = searchResult.resultList.toPersistentList()
                    )

                    _loading.value = false
                }
        }
    }

    fun updateIsFavorite(searchData: SearchData) {
        viewModelScope.launch {
            updateIsFavoriteUseCase(searchData, !searchData.isFavorite)
        }
    }
}