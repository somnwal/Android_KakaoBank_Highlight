package com.somnwal.android.kakaobank.app.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchRoute(
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val modelUiState by viewModel.modelUiState.collectAsStateWithLifecycle()

    // TODO (LaunchedEffect)

    SearchScreen(
        uiState = uiState,
        modelUiState = modelUiState,
        onQuery = { query, sort, page ->
            viewModel.search(query, sort, page)
        }
    )
}