package com.somnwal.android.kakaobank.app.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somnwal.android.kakaobank.app.core.domain.usecase.GetSearchResultUseCase
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase
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
            val searchResult = getSearchResultUseCase(query, sort, page)

            // 데이터가 없을경우
            if(_uiState.value == SearchUiState.Idle && searchResult.resultList.isEmpty()) {
                // TODO 에러 처리
            } else {
                _searchResultList = (_searchResultList + searchResult.resultList).sortedByDescending {
                    it.datetime
                }

                _uiState.value = SearchUiState.Success(
                    isNextPageExist = searchResult.isNextPageExist,
                    data = _searchResultList.toPersistentList()
                )
            }
        }
    }
}