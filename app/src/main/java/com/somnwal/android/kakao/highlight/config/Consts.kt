package com.somnwal.android.kakao.highlight.config

import com.somnwal.android.kakao.highlight.R

class Consts {
    companion object {
        const val ROUTE_SEARCH_SCREEN = "SEARCH"
        const val ROUTE_FAVORITE_SCREEN = "FAVORITE"

        val NAV_MENU = listOf(
            NavScreen.SearchScreen,
            NavScreen.FavoriteScreen
        )
    }
}

sealed class NavScreen(val route: String, val icon: Int) {
    data object SearchScreen : NavScreen(Consts.ROUTE_SEARCH_SCREEN, R.drawable.ic_search)
    data object FavoriteScreen : NavScreen(Consts.ROUTE_FAVORITE_SCREEN, R.drawable.ic_favorite)
}
