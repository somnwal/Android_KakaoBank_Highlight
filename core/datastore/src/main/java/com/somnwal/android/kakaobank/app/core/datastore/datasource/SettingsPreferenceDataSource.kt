package com.somnwal.android.kakaobank.app.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.somnwal.android.kakaobank.app.core.datastore.model.SettingsData
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SettingsPreferenceDataSource @Inject constructor(
    @Named("settings") private val dataStore: DataStore<Preferences>
) {

    object PreferencesKey {
        val IS_DARK_THEME = booleanPreferencesKey("IS_DARK_THEME")
    }

    val settingsData = dataStore.data.map { prefs ->
        SettingsData(
            isDarkTheme = prefs[PreferencesKey.IS_DARK_THEME] ?: false
        )
    }

    suspend fun updateIsDarTheme(isDarkTheme: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferencesKey.IS_DARK_THEME] = isDarkTheme
        }
    }
}