package com.somnwal.android.kakaobank.app.feature.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.somnwal.kakaobank.highlight.app.core.ui.icon.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakaoSearchBar(
    query: String = "",
    onQuery: () -> Unit,
    onQueryChange: (String) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearch = {
        keyboardController?.hide()
        onQuery()
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        query = query,
        placeholder = {
            Text(text = "검색어를 입력해주세요.")
        },
        onQueryChange = { changedQuery ->
            onQueryChange(changedQuery)
        },
        onSearch = {
            onSearch()
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearch()
                }
            ) {
                Icon(
                    imageVector = AppIcons.ICON_SEARCH_FILLED,
                    contentDescription = "검색"
                )
            }

        },
        active = false,
        onActiveChange = {},
        content = {}
    )
}