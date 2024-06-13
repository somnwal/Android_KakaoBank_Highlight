package com.somnwal.android.kakaobank.app.core.domain.usecase

import android.util.Log
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetSearchResultWithFavoriteUseCase @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
) {
    operator fun invoke(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<SearchData>> =
        getSearchResultUseCase(query, page, sort)
            .combine(getFavoriteListUseCase().distinctUntilChanged()) { searchResult, favoriteList ->
                searchResult.map {
                    it.copy().apply {
                        isFavorite = favoriteList.contains(it)
                    }
                }
            }

}