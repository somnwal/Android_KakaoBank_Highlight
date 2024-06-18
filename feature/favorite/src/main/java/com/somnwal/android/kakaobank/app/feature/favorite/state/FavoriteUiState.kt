package com.somnwal.android.kakaobank.app.feature.favorite.state

import androidx.compose.runtime.Immutable
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.collections.immutable.ImmutableList

sealed interface FavoriteUiState {

    @Immutable
    data object Idle: FavoriteUiState

    @Immutable
    data object Loading: FavoriteUiState

    @Immutable
    data object Empty: FavoriteUiState

    @Immutable
    data class Success(
        val data: List<SearchData>
    ) : FavoriteUiState
}