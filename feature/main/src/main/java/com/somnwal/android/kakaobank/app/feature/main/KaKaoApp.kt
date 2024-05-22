package com.somnwal.android.kakaobank.app.feature.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.somnwal.kakaobank.highlight.app.core.ui.common.navigation.BottomNavBar
import com.somnwal.kakaobank.highlight.app.core.ui.common.navigation.BottomNavBarItem
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.KakaoNavHost
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.NavScreen

@Composable
fun KakaoApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            KakaoBottomNavBar(
                onNavigate = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                            inclusive = false
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                currentDest = backStackEntry?.destination
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier.padding(contentPadding)
        ) {
            KakaoNavHost(
                navController = navController,
                startDestination = NavScreen.ROUTE_SEARCH_SCREEN
            )
        }

    }
}

@Composable
fun KakaoBottomNavBar(
    onNavigate: (NavScreen) -> Unit,
    currentDest: NavDestination?
) {
    BottomNavBar(
        modifier = Modifier
    ) {
        NavScreen.NAV_MENU_LIST.forEach { navMenu ->
            val selected = currentDest?.route == navMenu.route

            BottomNavBarItem(
                selected = selected,
                onClick = { onNavigate(navMenu) },
                selectedIcon = navMenu.selectedIcon,
                unselectedIcon = navMenu.unselectedIcon,
                label = navMenu.label
            )
        }
    }
}