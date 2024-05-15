package com.somnwal.android.kakaobank.app.data.repository.search

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    val observeSearchDataList : Flow<List<SearchData>>
}