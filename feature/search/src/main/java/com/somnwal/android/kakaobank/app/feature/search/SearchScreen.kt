package com.somnwal.android.kakaobank.app.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.state.SearchModelUiState
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import com.somnwal.kakaobank.highlight.app.core.ui.common.component.MediaItemCard

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    modelUiState: SearchModelUiState,
    onQuery: (String, String, Int) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
    ) {
        var queryState by rememberSaveable { mutableStateOf("고양이") }
        var sortState by rememberSaveable { mutableStateOf("recency") }
        var pageState by rememberSaveable { mutableIntStateOf(1) }

        KakaoSearchBar(
            query = queryState,
            onQuery = {
                onQuery(queryState, sortState, pageState)
            },
            onQueryChange = { changedQuery ->
                queryState = changedQuery
            }
        )

        when(uiState) {
            SearchUiState.IDLE,
            SearchUiState.SUCCESS -> {
                SearchResultContent(
                    modelUiState = modelUiState,
                    onItemClick = {

                    }
                )
            }
            SearchUiState.LOADING -> TODO()
            SearchUiState.ERROR -> TODO()
        }
    }
}

@Composable
internal fun SearchResultContent(
    modifier: Modifier = Modifier,
    modelUiState: SearchModelUiState,
    onItemClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(items = modelUiState.items) { index, item ->
            MediaItemCard(
                data = item
            )
        }
    }
}