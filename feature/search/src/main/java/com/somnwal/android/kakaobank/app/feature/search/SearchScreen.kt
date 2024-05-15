package com.somnwal.android.kakaobank.app.feature.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.somnwal.android.kakaobank.app.feature.search.component.KakaoSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUiState,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
    ) {
        KakaoSearchBar()

        when(state) {
            SearchUiState.Success -> SearchResultContent(items = state.data, onItemClick = )
        }
    }
}

@Composable
internal fun SearchResultContent(
    items: List<SearchUiState>,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        itemsIndexed(items = items) { index, item ->
            ListItem(
                headlineContent = { Text(text = item.toString()) },
                trailingContent = { },
                leadingContent = { },
                modifier = Modifier
                    .clickable {
                        onItemClick(0)
                    }
            )
        }
    }
}