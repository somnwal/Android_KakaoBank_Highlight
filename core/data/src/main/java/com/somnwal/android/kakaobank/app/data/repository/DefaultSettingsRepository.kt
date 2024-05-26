package com.somnwal.android.kakaobank.app.data.repository

import android.provider.Settings
import com.somnwal.android.kakaobank.app.core.datastore.datasource.SettingsPreferenceDataSource
import com.somnwal.kakaobank.app.core.data.repository.api.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences
import javax.inject.Inject

class DefaultSettingsRepository @Inject constructor(
    private val preferencesDataSource: SettingsPreferenceDataSource
) : SettingsRepository {
    override fun flowIsDarkTheme(): Flow<Boolean> =
        preferencesDataSource.settingsData.map { settingsData -> settingsData.isDarkTheme }

    override suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        preferencesDataSource.updateIsDarTheme(isDarkTheme)
    }
}