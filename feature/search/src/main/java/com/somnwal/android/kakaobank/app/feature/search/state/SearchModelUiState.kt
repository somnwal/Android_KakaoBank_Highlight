package com.somnwal.android.kakaobank.app.feature.search.state

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchDataType
import java.util.Date

data class SearchModelUiState (
    val query: String,
    val sort: String,
    val page: Int,
    val isNextPageExist: Boolean,
    val items: List<SearchData>
)