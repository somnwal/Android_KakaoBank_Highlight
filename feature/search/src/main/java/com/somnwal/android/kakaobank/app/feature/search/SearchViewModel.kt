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

    private var _searchResultList : List<SearchData> = listOf()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()

    val errorFlow
        get() = _errorFlow.asSharedFlow()

    fun search(query: String, sort: String, page: Int) {
        viewModelScope.launch {
            getSearchResultWithFavoriteUseCase(query, sort, page)
                .collectLatest { searchResult ->
                    Log.d("TEST1234", "before = _searchResultList : $_searchResultList")

                    _searchResultList = _searchResultList + searchResult.resultList

                    Log.d("TEST1234", "before = _searchResultList : $_searchResultList")

                    _uiState.value = SearchUiState.Success(
                        isNextPageExist = searchResult.isNextPageExist,
                        data = _searchResultList.toPersistentList()
                    )
                }
        }
    }

    fun updateIsFavorite(searchData: SearchData) {
        viewModelScope.launch {
            searchData.isFavorite = !searchData.isFavorite

            updateIsFavoriteUseCase(searchData, searchData.isFavorite)
        }
    }
}