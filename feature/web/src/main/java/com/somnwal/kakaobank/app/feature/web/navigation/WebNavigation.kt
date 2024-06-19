package com.somnwal.kakaobank.app.feature.web.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.somnwal.kakaobank.app.feature.web.WebRoute

fun NavController.navigateWeb(
    url: String,
    navOptions: NavOptions
) {
    val route = "${WebRoute.ROUTE}?${WebRoute.URL_ARG_ID}=$url"

    navigate(
        route,
        navOptions
    )
}

fun NavGraphBuilder.webNavGraph(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
) {
    composable(
        route = WebRoute.ROUTE_WITH_URL,
        arguments = listOf(
            navArgument(WebRoute.URL_ARG_ID) {
                defaultValue = null
                nullable = true
                type = NavType.StringType
            },
        )
    ) {
        WebRoute(
            padding = padding,
            onShowErrorSnackbar = onShowErrorSnackbar
        )
    }
}

object WebRoute {
    const val ROUTE = "web"
    const val URL_ARG_ID = "url"
    const val ROUTE_WITH_URL = "${ROUTE}?$URL_ARG_ID={$URL_ARG_ID}"
}