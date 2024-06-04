package com.somnwal.android.kakaobank.app.core.domain.usecase

import android.util.Log
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSearchResultWithFavoriteUseCase @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
) {

    suspend operator fun invoke(
        query: String,
        sort: String,
        page: Int
    ): Flow<SearchResult> =
        combine(
            getSearchResultUseCase(query, sort, page),
            getFavoriteListUseCase()
        ) { searchResult, favoriteList ->
            searchResult.apply {
                resultList.forEach { searchData ->
                    searchData.isFavorite = favoriteList.contains(searchData)
                }
            }
        }
}