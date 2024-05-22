package com.somnwal.android.kakaobank.app.feature.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.somnwal.android.kakaobank.app.feature.search.SearchRoute

@Composable
fun KakaoNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavScreen.SearchScreen.route) {
            SearchRoute(
                onNavigate = {

                }
            )
        }

        composable(NavScreen.FavoriteScreen.route) {
        }
    }
}