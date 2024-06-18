package com.somnwal.android.kakaobank.app.core.domain.usecase

import android.util.Log
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import kotlinx.coroutines.Dispatchers
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchResultWithFavoriteUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(
        query: String,
        page: Int,
        sort: String = "recency"
    ): Flow<List<SearchData>> = searchRepository.getSearchResult(query, page, sort)
        .withFavoriteList(favoriteRepository.favoriteList)

    private fun Flow<List<SearchData>>.withFavoriteList(favoriteList: Flow<List<SearchData>>): Flow<List<SearchData>> =
        combine(favoriteList) { searchResult, favoriteResult ->
            searchResult.map {
                it.apply {
                    this.isFavorite = this in favoriteResult
                }
            }
        }
}