package com.somnwal.android.kakaobank.app.core.domain.usecase

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String, sort: String, page: Int): SearchResult =
        searchRepository.getSearchResult(query, sort, page)
}