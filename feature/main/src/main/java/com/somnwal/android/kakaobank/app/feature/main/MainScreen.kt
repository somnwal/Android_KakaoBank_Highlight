package com.somnwal.android.kakaobank.app.feature.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import com.somnwal.android.kakaobank.app.feature.favorite.navigation.favoriteNavGraph
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.MainNavigator
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.MainTab
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.rememberMainNavigator
import com.somnwal.android.kakaobank.app.feature.search.navigation.searchNavGraph
import com.somnwal.kakaobank.app.feature.main.R
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    isDarkTheme: Boolean,
    onChangeTheme: (isDarkTheme: Boolean) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()
    val localContextResource = LocalContext.current.resources
    val onShowErrorSnackBar: (error: Throwable?) -> Unit = { error ->
        coroutineScope.launch {
            Log.e("ERROR", "내용 : ${error?.stackTraceToString()}")

            snackbarHostState.showSnackbar(
                when(error) {
                    else -> localContextResource.getString(R.string.error_message_unknown)
                }
            )
        }
    }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceDim)
            ) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination
                ) {
                    searchNavGraph(
                        padding = padding,
                        onShowErrorSnackbar = onShowErrorSnackBar,
                        isDarkTheme = isDarkTheme,
                        onChangeTheme = onChangeTheme,
                    )

                    favoriteNavGraph(
                        padding = padding,
                        onShowErrorSnackbar = onShowErrorSnackBar
                    )
                }
            }
        },
        bottomBar = {
            MainBottomBar(
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    )
}

@Composable
private fun MainBottomBar(
    tabs: List<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp
        )

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            tabs.forEach { tab ->
                MainBottomBarItem(
                    tab = tab,
                    selected = tab == currentTab,
                    onClick = { onTabSelected(tab) },
                )
            }
        }
    }
}

@Composable
private fun RowScope.MainBottomBarItem(
    modifier: Modifier = Modifier,
    tab: MainTab,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            val icon = if(selected) {
                tab.selectedIcon
            } else {
                tab.unselectedIcon
            }

            Icon(
                imageVector = icon,
                contentDescription = "icon"
            )
        },
        label = {
            Text(text = tab.contentDescription)
        }
    )
}