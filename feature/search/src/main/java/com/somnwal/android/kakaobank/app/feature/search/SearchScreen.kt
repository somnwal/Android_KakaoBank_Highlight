package com.somnwal.android.kakaobank.app.feature.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.component.MediaItemCard
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons
import com.somnwal.kakaobank.app.core.designsystem.component.LoadingBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (Throwable?) -> Unit,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.error.collectLatest { error -> onShowErrorSnackBar(error) }
    }

    SearchScreen(
        padding = padding,
        isDarkTheme = isDarkTheme,
        onChangeTheme = onChangeTheme,
        uiState = uiState,
        loading = loading,
        onQuery = { query ->
            viewModel.search(
                query = query,
                page = 1
            )
        },
        onNextPage = { query, page ->
            viewModel.search(
                query = query,
                page = page
            )
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
    loading: Boolean,
    onQuery: (String) -> Unit,
    onNextPage: (String, Int) -> Unit,
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
                    onQuery(queryState)
                },
                onQueryChange = { changedQuery ->
                    queryState = changedQuery
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when(uiState) {
                SearchUiState.Idle -> {

                }
                is SearchUiState.Success -> {
                    val data = uiState.data

                    LaunchedEffect(listState) {
                        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull() }
                            .collectLatest { lastVisibleItem ->
                                if(lastVisibleItem != null && !loading && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 1) {
                                    coroutineScope.launch {
                                        pageState += 1
                                        onNextPage(queryState, pageState)
                                    }
                                }
                            }
                    }

                    SearchScreenSuccessContent(
                        items = data,
                        listState = listState,
                        onItemClick = {
                            // TODO (열기)
                        },
                        onUpdateIsFavorite = onUpdateIsFavorite
                    )

                    LoadingBar(isLoading = loading)
                }
                // 비어있을 때와 에러가 발생했을 때
                SearchUiState.Empty,
                is SearchUiState.Error -> {
                    SearchScreenFailContent(
                        uiState = uiState
                    )
                }

                else -> {

                }
            }
        }
    }
}

@Composable
internal fun SearchScreenSuccessContent(
    items: ImmutableList<SearchData>,
    modifier: Modifier = Modifier,
    listState: LazyListState,
    onItemClick: (Int) -> Unit,
    onUpdateIsFavorite: (SearchData) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState,
    ) {
        itemsIndexed(items = items) { index, item ->
            MediaItemCard(
                data = item,
                onUpdateIsFavorite = onUpdateIsFavorite
            )
        }
    }
}

@Composable
internal fun SearchScreenFailContent(
    modifier: Modifier = Modifier,
    uiState: SearchUiState
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            textAlign = TextAlign.Center,
            text = when(uiState) {
                SearchUiState.Empty -> {
                    "조회결과가 존재하지 않습니다."
                }
                is SearchUiState.Error -> {
                    "조회 중 오류가 발생했습니다."
                }
                else -> {
                    ""
                }
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
internal fun SearchScreenFailContentPreivew() {
    SearchScreenFailContent(
        uiState = SearchUiState.Empty
    )
}