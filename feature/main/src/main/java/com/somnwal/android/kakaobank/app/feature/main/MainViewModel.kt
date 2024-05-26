package com.somnwal.android.kakaobank.app.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somnwal.kakaobank.app.core.data.repository.api.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isDarkTheme = settingsRepository.flowIsDarkTheme()

    fun updateIsDarkTheme(isDarkTheme: Boolean) =
        viewModelScope.launch {
            settingsRepository.updateIsDarkTheme(isDarkTheme)
        }
}