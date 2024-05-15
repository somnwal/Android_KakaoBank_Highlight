package com.somnwal.android.kakaobank.app.feature.search

sealed interface SearchUiState {
    object Loading : SearchUiState
    data class Success(val data: List<String>) : SearchUiState
}
