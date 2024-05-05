package com.somnwal.android.kakao.highlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.somnwal.android.kakao.highlight.component.BottomNavBar
import com.somnwal.android.kakao.highlight.component.MainNavHost
import com.somnwal.android.kakao.highlight.component.screen.FavoriteScreen
import com.somnwal.android.kakao.highlight.component.screen.SearchScreen
import com.somnwal.android.kakao.highlight.config.NavScreen
import com.somnwal.android.kakao.highlight.ui.theme.Android_KakaoBank_HighlightTheme
import com.somnwal.highlight.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_KakaoBank_HighlightTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}


@Composable
@Preview
fun MainApp() {
    val viewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavBar(
                onNavItemSelected = { navItem ->
                    navController.navigate(navItem.route) {
                        popUpTo(navItem.route)
                        launchSingleTop = true
                    }
                },
                selectedItem = backStackEntry?.destination
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding),
        ) {
            MainNavHost(
                navController = navController,
                viewModel = viewModel,
                startDestination = NavScreen.SearchScreen.route
            )
        }
    }
}