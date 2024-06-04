package com.somnwal.android.kakaobank.app.feature.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.somnwal.android.kakaobank.app.feature.favorite.navigation.navigateFavorite
import com.somnwal.android.kakaobank.app.feature.search.navigation.navigateSearch

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
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}