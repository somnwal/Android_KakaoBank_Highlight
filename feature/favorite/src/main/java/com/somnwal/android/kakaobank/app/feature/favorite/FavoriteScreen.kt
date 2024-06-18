package com.somnwal.android.kakaobank.app.feature.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.favorite.component.FavoriteItemCard
import com.somnwal.android.kakaobank.app.feature.favorite.navigation.FavoriteRoute
import com.somnwal.android.kakaobank.app.feature.favorite.state.FavoriteUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun FavoriteRoute(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val uistate by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {

    }

    FavoriteScreen(
        padding = padding,
        uiState = uistate,
        onUpdateIsFavorite = { searchData ->
            viewModel.updateIsFavorite(searchData)
        }
    )
}

@Composable
internal fun FavoriteScreen(
    padding: PaddingValues,
    uiState: FavoriteUiState,
    onUpdateIsFavorite: (SearchData) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
    ) {
        when(uiState) {
            is FavoriteUiState.Success -> {
                FavoriteListContent(
                    uiState = uiState,
                    onUpdateIsFavorite = onUpdateIsFavorite
                )
            }
            else -> Unit
        }
    }
}

@Composable
internal fun FavoriteListContent(
    uiState: FavoriteUiState.Success,
    modifier: Modifier = Modifier,
    onUpdateIsFavorite: (SearchData) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            count = uiState.data.size,
            key = { index -> index }
        ) { index ->
            FavoriteItemCard(
                data = uiState.data[index],
                onUpdateIsFavorite = onUpdateIsFavorite,
            )
        }
    }
}