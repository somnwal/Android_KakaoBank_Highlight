package com.somnwal.android.kakaobank.app.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.MainNavigator
import com.somnwal.android.kakaobank.app.feature.main.ui.navigation.rememberMainNavigator
import com.somnwal.kakaobank.app.core.designsystem.theme.KakaoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme by mainViewModel.isDarkTheme.collectAsStateWithLifecycle(initialValue = false, this)
            val navigator: MainNavigator = rememberMainNavigator()

            KakaoTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    navigator = navigator,
                    isDarkTheme = isDarkTheme,
                    onChangeTheme = mainViewModel::updateIsDarkTheme
                )
            }
        }
    }
}