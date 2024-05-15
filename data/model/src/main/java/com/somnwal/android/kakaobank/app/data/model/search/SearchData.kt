package com.somnwal.android.kakaobank.app.data.model.search

enum class SearchDataType {
    IMAGE,
    VIDEO
}

data class SearchData (
    val id: Long,
    val type: SearchDataType,
    val imgUrl: String?,
    val timestamp: Long,
    val isFavorite: Boolean = false
)