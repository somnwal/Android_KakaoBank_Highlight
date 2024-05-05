package com.somnwal.android.kakao.highlight.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.somnwal.android.kakao.highlight.component.screen.FavoriteScreen
import com.somnwal.android.kakao.highlight.component.screen.SearchScreen
import com.somnwal.android.kakao.highlight.config.NavScreen
import com.somnwal.highlight.viewmodel.MainViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    startDestination: String,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(NavScreen.SearchScreen.route) {
            SearchScreen(navController = navController, viewModel = viewModel)
        }
        composable(NavScreen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController, viewModel = viewModel)
        }
    }
}