package com.somnwal.android.kakaobank.app.feature.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.search.SearchDetailRoute
import com.somnwal.android.kakaobank.app.feature.search.SearchRoute
import kotlinx.serialization.json.Json

fun NavController.navigateSearch(
    navOptions: NavOptions
) {
    navigate(
        SearchRoute.ROUTE,
        navOptions
    )
}

fun NavController.navigateSearchDetail(
    navOptions: NavOptions,
    searchData: SearchData
) {
    navigate(
        SearchDetailRoute.with(searchData),
        navOptions
    )
}

fun NavGraphBuilder.searchNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    isDarkTheme: Boolean,
    onChangeTheme: (Boolean) -> Unit
) {
    composable(
        route = SearchRoute.ROUTE
    ) {
        SearchRoute(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackbar,
            isDarkTheme = isDarkTheme,
            onChangeTheme = onChangeTheme
        )
    }
}

fun NavGraphBuilder.searchDetailNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    composable(
        route = SearchDetailRoute.ROUTE,
    ) { backStackEntry ->

        val searchData =
            Json.decodeFromString<SearchData>(backStackEntry.arguments?.getString("searchData") ?: "")

        SearchDetailRoute(
            padding = padding,
            onShowErrorSnackBar = onShowErrorSnackbar,
            searchData = searchData
        )
    }
}

object SearchRoute {
    const val ROUTE = "search"
}

object SearchDetailRoute {
    const val ROUTE = "searchDetail"

    fun with(searchData: SearchData) : String =
        "${ROUTE}?searchData=${Json.encodeToString(SearchData.serializer(), searchData)}"
}