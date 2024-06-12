package com.somnwal.android.kakaobank.app.feature.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.favorite.navigation.navigateFavorite
import com.somnwal.android.kakaobank.app.feature.search.navigation.navigateSearch
import com.somnwal.android.kakaobank.app.feature.search.navigation.navigateSearchDetail

internal class MainNavigator(
    val navController: NavHostController
) {

    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = MainTab.SEARCH.route

    val currentTab: MainTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainTab::find)

    fun navigate(tab: MainTab) {
        // TODO 개념정리
        val navOptions = navOptions {
            // 시작화면 까지 스택을 전부 팝업한다.
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }

        when(tab) {
            MainTab.SEARCH -> navController.navigateSearch(navOptions)
            MainTab.FAVORITE -> navController.navigateFavorite(navOptions)
        }
    }

    private fun popBackStack() {
        navController.popBackStack()
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainTab
    }

    fun navigateSearchDetail(searchData: SearchData) {
        val navOptions = navOptions {
            launchSingleTop = true
            restoreState = true
        }

        navController.navigateSearchDetail(navOptions, searchData)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}