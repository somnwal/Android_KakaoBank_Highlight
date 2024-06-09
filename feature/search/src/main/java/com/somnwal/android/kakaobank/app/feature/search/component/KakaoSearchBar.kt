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
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KakaoSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val onSearchTrigger = {
        keyboardController?.hide()
        onSearch()
    }

    SearchBar(
        modifier = modifier
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
            onSearchTrigger()
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onSearchTrigger()
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