package com.somnwal.kakaobank.highlight.app.ui.navigation
//
sealed class NavScreen(val route: String) {
    companion object {
        const val ROUTE_SEARCH_SCREEN = "SEARCH"
        const val ROUTE_FAVORITE_SCREEN = "FAVORITE"
    }

    data object SearchScreen : NavScreen(ROUTE_SEARCH_SCREEN)
}