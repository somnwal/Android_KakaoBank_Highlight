package com.somnwal.kakaobank.app.feature.web.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.somnwal.android.kakaobank.app.data.model.search.SearchData

@Stable
sealed interface WebUiState {

    @Immutable
    data object Idle: WebUiState

    @Immutable
    data class Success(
        val data: List<SearchData>
    ) : WebUiState

    @Immutable
    data class Error(
        val data: Throwable?
    ): WebUiState
}
