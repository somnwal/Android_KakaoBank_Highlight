package com.somnwal.android.kakaobank.app.feature.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import kotlinx.coroutines.flow.collectLatest

const val SEARCH_DATA = "searchData"

@Composable
internal fun SearchDetailRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (Throwable?) -> Unit,
    searchData: SearchData,
    viewModel: SearchDetailViewModel = hiltViewModel()
) {


//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//
//    SearchDetailScreen(
//        padding = padding,
//        uiState = uiState,
//    )
}

//@Composable
//internal fun SearchDetailScreen(
//)