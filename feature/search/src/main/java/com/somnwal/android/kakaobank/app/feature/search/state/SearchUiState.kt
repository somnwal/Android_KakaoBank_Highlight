package com.somnwal.android.kakaobank.app.feature.search.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.somnwal.android.kakaobank.app.data.model.search.SearchData

@Stable
sealed interface SearchUiState {

    @Immutable
    data object Idle: SearchUiState

    @Immutable
    data object Empty: SearchUiState

    @Immutable
    data class Success(
        val data: List<SearchData>
    ) : SearchUiState

    @Immutable
    data class Error(
        val data: Throwable?
    ): SearchUiState
}