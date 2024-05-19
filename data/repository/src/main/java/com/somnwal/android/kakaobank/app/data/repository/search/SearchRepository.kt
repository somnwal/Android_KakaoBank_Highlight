package com.somnwal.android.kakaobank.app.data.repository.search

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchResult(query: String, sort: String, page: Int) : SearchResult
}
