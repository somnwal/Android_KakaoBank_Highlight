package com.somnwal.kakaobank.app.core.data.repository.api

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val isDarkTheme : Flow<Boolean>

    suspend fun updateIsDarkTheme(
        isDarkTheme: Boolean
    )
}