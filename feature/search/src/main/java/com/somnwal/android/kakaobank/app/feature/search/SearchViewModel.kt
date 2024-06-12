package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.replay
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import java.util.Date
import javax.inject.Inject

private const val SEARCH_QUERY = "searchQuery"
private const val SEARCH_PAGE = "searchPage"
private const val SEARCH_LOADING = "searchLoading"
private const val SEARCH_TIME = "searchTime"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultWithFavoriteUseCase: GetSearchResultWithFavoriteUseCase,
    private val updateIsFavoriteUseCase: UpdateIsFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var searchQuery = savedStateHandle.getStateFlow(key = SEARCH_QUERY, initialValue = "")
    val searchPage = savedStateHandle.getStateFlow(key = SEARCH_PAGE, initialValue = 1)
    val searchLoading = savedStateHandle.getStateFlow(key = SEARCH_LOADING, initialValue = false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState : StateFlow<SearchUiState> =
        searchQuery.flatMapLatest { query ->
            searchPage.flatMapLatest { page ->
                searchLoading.flatMapLatest { loading ->
                    if (loading) {
                        flowOf(SearchUiState.Loading)
                    } else {
                        getSearchResultWithFavoriteUseCase(
                            query = query,
                            page = page,
                            sort = "recency"
                        ).map<List<SearchData>, SearchUiState> { data ->
                            if (data.isEmpty()) {
                                SearchUiState.Empty
                            } else {
                                SearchUiState.Success(
                                    data = data
                                )
                            }
                        }.catch { error ->
                            emit(SearchUiState.Error(error))
                        }
                    }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchUiState.Idle
        )


    fun onSearch(query: String) {
        setLoading(true)
        savedStateHandle[SEARCH_QUERY] = query
        savedStateHandle[SEARCH_PAGE] = 1
        setLoading(false)
    }

    fun onNextPage() {
        savedStateHandle[SEARCH_PAGE] = searchPage.value + 1
    }

    fun setLoading(isLoading: Boolean) {
        savedStateHandle[SEARCH_LOADING] = isLoading
    }

    fun updateIsFavorite(searchData: SearchData) {
        viewModelScope.launch {
            updateIsFavoriteUseCase(searchData, !searchData.isFavorite)
        }
    }
}