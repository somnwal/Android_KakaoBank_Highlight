package com.somnwal.android.kakaobank.app.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class FavoritePreferencesDataSource @Inject constructor(
    @Named("favorite") private val dataStore: DataStore<Preferences>
) {
    object PreferenceKey {
        val FAVORITE_LIST = stringSetPreferencesKey("FAVORITE_LIST")
    }

    val favoriteList = dataStore.data.map { prefs ->
        prefs[PreferenceKey.FAVORITE_LIST] ?: emptySet()
    }

    suspend fun updateFavoriteList(favoriteList: Set<String>) {
        dataStore.edit { prefs ->
            prefs[PreferenceKey.FAVORITE_LIST] = favoriteList
        }
    }
}