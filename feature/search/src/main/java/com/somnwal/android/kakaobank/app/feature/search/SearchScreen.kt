package com.somnwal.android.kakaobank.app.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize(),
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            query = "",
            placeholder = {
                Text(text = "검색어를 입력해주세요.")
            },
            onQueryChange = {

            },
            onSearch = {

            },
            active = false,
            onActiveChange = {},
            content = {}
        )


        Text(text = "!1")
    }
}