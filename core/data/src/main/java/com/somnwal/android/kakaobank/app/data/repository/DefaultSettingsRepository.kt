package com.somnwal.android.kakaobank.app.data.repository

import com.somnwal.android.kakaobank.app.core.datastore.datasource.KakaoPreferencesDataSource
import com.somnwal.kakaobank.app.core.data.repository.api.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    private val kakaoPreferencesDataSource: KakaoPreferencesDataSource
) : SettingsRepository {

    override val isDarkTheme: Flow<Boolean> =
        kakaoPreferencesDataSource.prefs.map { prefs ->
            prefs.isDarkTheme
        }

    override suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        kakaoPreferencesDataSource.updateIsDarkTheme(isDarkTheme)
    }
}