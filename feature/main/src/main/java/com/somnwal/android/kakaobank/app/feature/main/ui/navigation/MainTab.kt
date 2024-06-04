package com.somnwal.android.kakaobank.app.feature.main.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.somnwal.android.kakaobank.app.feature.favorite.navigation.FavoriteRoute
import com.somnwal.android.kakaobank.app.feature.search.navigation.SearchRoute
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons

internal enum class MainTab(
    val route: String,
    internal val contentDescription: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    SEARCH(
        route = SearchRoute.ROUTE,
        contentDescription = "검색",
        unselectedIcon = AppIcons.ICON_SEARCH_OUTLINED,
        selectedIcon = AppIcons.ICON_SEARCH_FILLED
    ),

    FAVORITE(
        route = FavoriteRoute.ROUTE,
        contentDescription = "관심 목록",
        unselectedIcon = AppIcons.ICON_FAVORITE_OUTLINED,
        selectedIcon = AppIcons.ICON_FAVORITE_FILLED
    );

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainTab? {
            return entries.find { it.route == route }
        }
    }
}