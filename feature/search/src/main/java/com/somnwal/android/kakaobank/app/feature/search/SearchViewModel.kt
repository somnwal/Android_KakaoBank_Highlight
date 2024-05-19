package com.somnwal.android.kakaobank.app.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.repository.search.SearchRepository
import com.somnwal.android.kakaobank.app.feature.search.state.SearchModelUiState
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.IDLE)

    val uiState : StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private val _modelUiState : MutableStateFlow<SearchModelUiState> =
        MutableStateFlow(
            SearchModelUiState("", "recency", 1, false, listOf())
        )

    val modelUiState : StateFlow<SearchModelUiState>
        get() = _modelUiState.asStateFlow()

    fun search(query: String, sort: String, page: Int) {
        viewModelScope.launch {
            val resultData = repository.getSearchResult(query, sort, page)

            _modelUiState.value = SearchModelUiState(
                query = query,
                sort = sort,
                page = page,
                isNextPageExist = resultData.isNextPageExist,
                items = resultData.resultList
            )
        }
    }
}