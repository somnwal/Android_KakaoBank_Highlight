package com.somnwal.kakaobank.highlight.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.somnwal.android.kakaobank.app.feature.favorite.FavoriteScreen
import com.somnwal.android.kakaobank.app.feature.search.SearchScreen

@Composable
fun KakaoNavHost(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavScreen.SearchScreen.route) {
            SearchScreen()
        }

        composable(NavScreen.FavoriteScreen.route) {
            FavoriteScreen()
        }
    }
}