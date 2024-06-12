package com.somnwal.kakaobank.app.core.data.repository.api

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchResult(
        query: String,
        page: Int,
        sort: String
    ) : Flow<List<SearchData>>
}
