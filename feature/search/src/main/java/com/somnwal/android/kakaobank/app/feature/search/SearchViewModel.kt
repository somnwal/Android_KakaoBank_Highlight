package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somnwal.android.kakaobank.app.data.repository.search.SearchRepository
import com.somnwal.android.kakaobank.app.feature.search.state.SearchModelUiState
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
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

    fun search(query: String, sort: String, page: Int, isFirst: Boolean = false) {
        viewModelScope.launch {
            repository.getSearchResult(query, sort, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchResult ->
                    Log.d("테스트", "query: $query | page : $page | hasNext : ${searchResult.isNextPageExist}")
                    Log.d("테스트", searchResult.resultList.toString())

                    var newList = if(isFirst) {
                        searchResult.resultList
                    } else {
                        modelUiState.value.items + searchResult.resultList
                    }

                    newList = newList.sortedByDescending { it.datetime }

                    _modelUiState.value = SearchModelUiState(
                        query = query,
                        sort = sort,
                        page = page,
                        isNextPageExist = searchResult.isNextPageExist,
                        items = newList
                    )
                }, {

                })
        }
    }
}