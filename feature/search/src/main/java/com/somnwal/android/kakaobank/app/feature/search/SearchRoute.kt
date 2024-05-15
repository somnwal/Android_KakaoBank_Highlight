package com.somnwal.android.kakaobank.app.feature.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel

@Composable
fun SearchRoute(
    onNavigate: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel
) {
    SearchScreen()
}