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
import kotlinx.coroutines.Dispatchers
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
import kotlinx.coroutines.flow.flattenMerge
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

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultWithFavoriteUseCase: GetSearchResultWithFavoriteUseCase,
    private val updateIsFavoriteUseCase: UpdateIsFavoriteUseCase,
) : ViewModel() {

    var _query = MutableStateFlow("")
    val _page = MutableStateFlow(1)


    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    private val _loadingState = MutableStateFlow(false)
    private val _errorState = MutableStateFlow<Throwable?>(null)

    val uiState : StateFlow<SearchUiState> = _uiState.asStateFlow()
    val loadingState : StateFlow<Boolean> = _loadingState.asStateFlow()
    val errorState : StateFlow<Throwable?> = _errorState.asStateFlow()

    fun onSearch(query: String, page: Int = 1) {
        Log.d("로그", "Call onSearch >>>")
        setLoading(true)

        _query.value = query
        _page.value = page

        Log.d("로그", """doSearch >>>
            query : ${_query.value}
            page  : ${_page.value}
        """.trimIndent())

        viewModelScope.launch {
            getSearchResultWithFavoriteUseCase(_query.value, _page.value, "recency")
                .collectLatest { data ->
                    Log.d("로그", "data : $data")

                    if(data.isEmpty()) {
                        _uiState.value = SearchUiState.Empty
                    } else {
                        _uiState.value = SearchUiState.Success(data = data)
                    }

                    setLoading(false)
                }
        }
    }

    fun onNextPage() {
        onSearch(_query.value, _page.value + 1)
    }

    fun setLoading(isLoading: Boolean) {
        _loadingState.value = isLoading
    }

    fun updateIsFavorite(searchData: SearchData) {
        viewModelScope.launch {
            updateIsFavoriteUseCase(searchData, !searchData.isFavorite)
        }
    }
}