package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.compose.foundation.SurfaceCoroutineScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.state.SearchModelUiState
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import com.somnwal.kakaobank.highlight.app.core.ui.common.component.MediaItemCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.logging.Logger

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    modelUiState: SearchModelUiState,
    onQuery: (String, String, Int, Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

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
                onQuery(queryState, sortState, pageState, true)
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
                    listState = listState,
                    coroutineScope = coroutineScope,
                    onItemClick = {

                    },
                    onNextPage = {
                        Log.d("SearchScreen", "다음 페이지 호출 >>")
                        pageState += 1

                        onQuery(queryState, sortState, pageState, false)
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
    listState: LazyListState,
    coroutineScope: CoroutineScope,
    onItemClick: (Int) -> Unit,
    onNextPage: () -> Unit,
) {
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
            .collect { lastVisibleItem ->
                if(lastVisibleItem != null && lastVisibleItem.index == listState.layoutInfo.totalItemsCount - 1) {
                    Log.d("테스트", "스크롤 끝")

                    onNextPage()
                }
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(items = modelUiState.items) { index, item ->
            MediaItemCard(
                data = item
            )
        }
    }
}