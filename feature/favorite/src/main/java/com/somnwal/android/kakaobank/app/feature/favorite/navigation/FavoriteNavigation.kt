package com.somnwal.android.kakaobank.app.feature.favorite.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.somnwal.android.kakaobank.app.feature.favorite.FavoriteRoute

fun NavController.navigateFavorite(navOptions: NavOptions) {
    navigate(FavoriteRoute.ROUTE)
}

fun NavGraphBuilder.favoriteNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    composable(route = FavoriteRoute.ROUTE) {
        FavoriteRoute(
            padding = padding,
            onShowErrorSnackbar = onShowErrorSnackbar,
        )
    }
}

object FavoriteRoute {
    const val ROUTE = "favorite"
}