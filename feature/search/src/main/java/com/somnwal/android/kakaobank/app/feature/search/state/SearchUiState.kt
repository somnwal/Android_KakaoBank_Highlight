package com.somnwal.android.kakaobank.app.feature.search.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface SearchUiState {

    @Immutable
    data object Idle: SearchUiState

    @Immutable
    data object Loading: SearchUiState

    @Immutable
    data object Empty: SearchUiState

    @Immutable
    data class Success(
        val isNextPageExist: Boolean,
        val data: ImmutableList<SearchData>
    ) : SearchUiState

    @Immutable
    data class Error(
        val error: Throwable?
    ) : SearchUiState
}