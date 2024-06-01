package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.component.MediaItemCard
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons
import com.somnwal.kakaobank.app.core.designsystem.theme.KakaoTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (Throwable?) -> Unit,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { error -> onShowErrorSnackBar(error) }
    }

    SearchScreen(
        padding = padding,
        isDarkTheme = isDarkTheme,
        onChangeTheme = onChangeTheme,
        uiState = uiState,
        onQuery = { query, sort, page ->
            viewModel.search(query, sort, page)
        },
        onUpdateIsFavorite = { searchData ->
            viewModel.updateIsFavorite(searchData)
        }
    )
}

@Composable
fun SearchScreen(
    padding: PaddingValues,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit,
    uiState: SearchUiState,
    onQuery: (String, String, Int) -> Unit,
    onUpdateIsFavorite: (SearchData) -> Unit,
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            val icon = if(isDarkTheme) {
                AppIcons.ICON_DARK_THEME_OUTLINED
            } else {
                AppIcons.ICON_LIGHT_THEME_OUTLINED
            }

            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(
                        onClick = {
                            onChangeTheme(!isDarkTheme)
                        }
                    ),
                imageVector = icon,
                tint = if(isDarkTheme) { Color.White } else { Color.Black },
                contentDescription = "테마 변경"
            )

            KakaoSearchBar(
                modifier = Modifier
                    .padding(start = 8.dp),
                query = queryState,
                onQuery = {
                    onQuery(queryState, sortState, pageState)
                },
                onQueryChange = { changedQuery ->
                    queryState = changedQuery
                }
            )
        }


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
                    },
                    onUpdateIsFavorite = onUpdateIsFavorite
                )
            }
            SearchUiState.Loading -> TODO()
            is SearchUiState.Error -> TODO()
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
    onUpdateIsFavorite: (SearchData) -> Unit,
) {
    val FETCH_NEXT_COUNT = 25

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
            .collect { lastVisibleItem ->
                if(lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - FETCH_NEXT_COUNT) {
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
                data = item,
                onUpdateIsFavorite = onUpdateIsFavorite
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SearchScreenPreview() {
    KakaoTheme {
        SearchScreen(
            padding = PaddingValues(0.dp),
            isDarkTheme = false,
            onChangeTheme = { },
            uiState = SearchUiState.Success(
                isNextPageExist = false,
                data = immutableListOf()
            ),
            onQuery = { query, sort, page ->

            },
            onUpdateIsFavorite = {

            }
        )
    }

}