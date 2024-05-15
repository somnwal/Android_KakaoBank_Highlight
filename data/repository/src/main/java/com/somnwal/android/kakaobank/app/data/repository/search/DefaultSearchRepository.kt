package com.somnwal.android.kakaobank.app.data.repository.search

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor() : SearchRepository {
    override val observeSearchDataList: Flow<List<SearchData>>
        get() = TODO("Not yet implemented")
}