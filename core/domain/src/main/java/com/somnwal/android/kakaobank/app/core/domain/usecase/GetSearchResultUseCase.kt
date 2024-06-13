package com.somnwal.android.kakaobank.app.core.domain.usecase

import android.util.Log
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<SearchData>> = searchRepository.getSearchResult(query, page, sort)
}