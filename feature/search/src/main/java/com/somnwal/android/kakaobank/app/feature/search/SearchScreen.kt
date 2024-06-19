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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchDataType
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar
import com.somnwal.android.kakaobank.app.feature.search.component.SearchItemCard
import com.somnwal.android.kakaobank.app.feature.search.state.SearchUiState
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons
import com.somnwal.kakaobank.app.core.designsystem.component.LoadingBar
import com.somnwal.kakaobank.app.core.designsystem.theme.KakaoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SearchRoute(
    padding: PaddingValues,
    onShowErrorSnackBar: (Throwable?) -> Unit,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit,
    onItemClick: (SearchData) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()

    LaunchedEffect(errorState) {
        viewModel.errorState.collectLatest { error ->
            error?.let(onShowErrorSnackBar)
        }
    }

    SearchScreen(
        padding = padding,
        isDarkTheme = isDarkTheme,
        onChangeTheme = onChangeTheme,
        uiState = uiState,
        onSearch = viewModel::onSearch,
        onNextPage = viewModel::onNextPage,
        onUpdateIsFavorite = viewModel::updateIsFavorite,
        onItemClick = onItemClick
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
    onItemClick: (SearchData) -> Unit,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        var queryState by rememberSaveable { mutableStateOf("고양이") }
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
                is SearchUiState.Success -> {
                    SearchSuccessContent(
                        uiState = uiState,
                        listState = listState,
                        onNextPage = onNextPage,
                        onUpdateIsFavorite = onUpdateIsFavorite,
                        onItemClick = onItemClick
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

            /*if(loadingState) {
                LoadingBar()
            }*/
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
    onItemClick: (SearchData) -> Unit,
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

            SearchItemCard(
                data = uiState.data[index],
                onUpdateIsFavorite = onUpdateIsFavorite,
                onItemClick = onItemClick
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

@Preview(
    device = Devices.PHONE,
    showBackground = true
)
@Composable
fun SearchScreenSuccessPreview() {
    KakaoTheme {
        SearchScreen(
            padding = PaddingValues(),
            isDarkTheme = false,
            onChangeTheme = { },
            uiState = SearchUiState.Success(
                data = listOf(
                    SearchData(
                        type =  SearchDataType.IMAGE,
                        title = "이미지",
                        thumbnailUrl = "",
                        url = "",
                        datetime = "9999-12-31"
                    ),
                    SearchData(
                    type =  SearchDataType.VIDEO,
                    title = "비디오",
                    thumbnailUrl = "",
                    url = "",
                    datetime = "9999-12-31"
                    ).apply { isFavorite = true },
                    SearchData(
                        type =  SearchDataType.VIDEO,
                        title = "비디오",
                        thumbnailUrl = "",
                        url = "",
                        datetime = "9999-12-31"
                    )
                )
            ),
            onSearch = { },
            onNextPage = { /*TODO*/ },
            onUpdateIsFavorite = { },
            onItemClick = { }
        )
    }
}

@Preview(
    device = Devices.PHONE,
    showBackground = true
)
@Composable
fun SearchScreenEmptyPreview() {
    KakaoTheme {
        SearchScreen(
            padding = PaddingValues(),
            isDarkTheme = false,
            onChangeTheme = { },
            uiState = SearchUiState.Empty,
            onSearch = { },
            onNextPage = { /*TODO*/ },
            onUpdateIsFavorite = { },
            onItemClick = { }
        )
    }
}

@Preview(
    device = Devices.PHONE,
    showBackground = true
)
@Composable
fun SearchScreenFailPreview() {
    KakaoTheme {
        SearchScreen(
            padding = PaddingValues(),
            isDarkTheme = false,
            onChangeTheme = { },
            uiState = SearchUiState.Error(Throwable("!!")),
            onSearch = { },
            onNextPage = { /*TODO*/ },
            onUpdateIsFavorite = { },
            onItemClick = { }
        )
    }
}