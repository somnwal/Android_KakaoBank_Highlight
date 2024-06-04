package com.somnwal.android.kakaobank.app.feature.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.somnwal.android.kakaobank.app.feature.search.SearchRoute

fun NavController.navigateSearch(navOptions: NavOptions) {
    navigate(SearchRoute.ROUTE)
}

fun NavGraphBuilder.searchNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit
) {
    composable(route = SearchRoute.ROUTE) {
        SearchRoute(padding, onShowErrorSnackbar, isDarkTheme, onChangeTheme)
    }
}

object SearchRoute {
    const val ROUTE = "search"
}