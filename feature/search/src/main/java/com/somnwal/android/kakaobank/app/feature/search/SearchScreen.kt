package com.somnwal.android.kakaobank.app.feature.search

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.component.MediaItemCard
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons
import com.somnwal.kakaobank.app.core.designsystem.component.LoadingBar
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
        viewModel.uiState.collectLatest { state ->
            if (state is SearchUiState.Error) {
                onShowErrorSnackBar(state.data)
            }
        }
    }

    SearchScreen(
        padding = padding,
        isDarkTheme = isDarkTheme,
        onChangeTheme = onChangeTheme,
        uiState = uiState,
        onSearch = viewModel::onSearch,
        onNextPage = viewModel::onNextPage,
        onUpdateIsFavorite = viewModel::updateIsFavorite
    )
}

@Composable
fun SearchScreen(
    padding: PaddingValues,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit,
    uiState: SearchUiState,
    onSearch: (String) -> Unit,
    onNextPage: () -> Unit,
    onUpdateIsFavorite: (SearchData) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        var queryState by rememberSaveable { mutableStateOf("") }
        val listState = rememberLazyListState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // 다크모드 | 라이트모드 아이콘
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
                onQueryChange = { query ->
                    queryState = query
                },
                onSearch = {
                    onSearch(queryState)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when(uiState) {
                SearchUiState.Loading -> {
                    LoadingBar()
                }
                is SearchUiState.Success -> {
                    SearchSuccessContent(
                        uiState = uiState,
                        listState = listState,
                        onNextPage = onNextPage,
                        onUpdateIsFavorite = onUpdateIsFavorite,
                        onItemClick = {
                            // TODO (열기)
                        }
                    )
                }
                // 비어있을 때와 에러가 발생했을 때
                SearchUiState.Empty,
                is SearchUiState.Error -> {
                    SearchFailContent(
                        uiState = uiState
                    )
                }
                else -> Unit
            }
        }
    }
}

@Composable
internal fun SearchSuccessContent(
    modifier: Modifier = Modifier,
    uiState: SearchUiState.Success,
    listState: LazyListState = rememberLazyListState(),
    onNextPage: () -> Unit,
    onUpdateIsFavorite: (SearchData) -> Unit,
    onItemClick: (Int) -> Unit,
) {
    val loadNextPage by remember(uiState) {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) >=
                    uiState.data.size - 1
        }
    }

    LaunchedEffect(loadNextPage) {
        if(loadNextPage) {
            onNextPage()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState,
    ) {
        items(
            count = uiState.data.size,
            key = { index -> index }
        ) { index ->

            val item = uiState.data[index]

            MediaItemCard(
                data = item,
                onUpdateIsFavorite = onUpdateIsFavorite
            )
        }
    }
}

@Composable
internal fun SearchFailContent(
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