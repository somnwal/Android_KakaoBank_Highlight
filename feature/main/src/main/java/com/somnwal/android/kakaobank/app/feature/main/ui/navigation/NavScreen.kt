package com.somnwal.android.kakaobank.app.feature.main.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.somnwal.kakaobank.highlight.app.core.ui.icon.AppIcons

//
sealed class NavScreen(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String) {
    companion object {
        const val ROUTE_SEARCH_SCREEN = "SEARCH"
        const val ROUTE_FAVORITE_SCREEN = "FAVORITE"

        val NAV_MENU_LIST = listOf(
            SearchScreen,
            FavoriteScreen
        )
    }

    data object SearchScreen : NavScreen(ROUTE_SEARCH_SCREEN, AppIcons.ICON_SEARCH_FILLED, AppIcons.ICON_SEARCH_OUTLINED, ROUTE_SEARCH_SCREEN)
    data object FavoriteScreen : NavScreen(ROUTE_FAVORITE_SCREEN, AppIcons.ICON_FAVORITE_FILLED, AppIcons.ICON_FAVORITE_OUTLINED, ROUTE_FAVORITE_SCREEN)
}