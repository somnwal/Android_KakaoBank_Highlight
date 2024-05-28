package com.somnwal.kakaobank.app.core.data.repository.api

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchResult(query: String, sort: String, page: Int) : Flow<SearchResult>
}
