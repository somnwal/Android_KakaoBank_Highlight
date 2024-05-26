package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.component.MediaItemCard
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (Throwable?) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { error -> onShowErrorSnackBar(error) }
    }

    SearchScreen(
        padding = padding,
        uiState = uiState,
        onQuery = { query, sort, page ->
            viewModel.search(query, sort, page)
        }
    )
}

@Composable
fun SearchScreen(
    padding: PaddingValues,
    uiState: SearchUiState,
    onQuery: (String, String, Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
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
            SearchUiState.Idle -> {

            }
            is SearchUiState.Success -> {
                val data = uiState.data

                SearchResultContent(
                    items = data,
                    listState = listState,
                    coroutineScope = coroutineScope,
                    onItemClick = {

                    },
                    onNextPage = {
                        Log.d("SearchScreen", "다음 페이지 호출 >>")
                        pageState += 1

                        onQuery(queryState, sortState, pageState)
                    }
                )
            }
            SearchUiState.Loading -> TODO()
            SearchUiState.Empty -> TODO()
        }
    }
}

@Composable
internal fun SearchResultContent(
    items: ImmutableList<SearchData>,
    modifier: Modifier = Modifier,
    listState: LazyListState,
    coroutineScope: CoroutineScope,
    onItemClick: (Int) -> Unit,
    onNextPage: () -> Unit,
) {
    val FETCH_NEXT_COUNT = 25

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
            .collect { lastVisibleItem ->
                if(lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - FETCH_NEXT_COUNT) {
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
        itemsIndexed(items = items) { index, item ->
            MediaItemCard(
                data = item
            )
        }
    }
}