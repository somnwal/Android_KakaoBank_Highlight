package com.somnwal.android.kakaobank.app.data.model.search

import java.util.Date

enum class SearchDataType {
    IMAGE,
    VIDEO
}

data class SearchResult (
    val isNextPageExist: Boolean,
    val resultList: List<SearchData>
)

data class SearchData (
    val type: SearchDataType,
    val title: String,

    val thumbnailUrl: String,
    val imageUrl: String,
    val docUrl: String,

    val timestamp: Date,
    val isFavorite: Boolean = false
)