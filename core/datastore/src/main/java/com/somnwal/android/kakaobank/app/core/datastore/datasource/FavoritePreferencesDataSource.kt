package com.somnwal.android.kakaobank.app.core.datastore.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toSet
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Named

class FavoritePreferencesDataSource @Inject constructor(
    @Named("favorite") private val dataStore: DataStore<Preferences>
) {
    object PreferenceKey {
        val FAVORITE_LIST = stringSetPreferencesKey("FAVORITE_LIST")
    }

    val favoriteList = dataStore.data.map { prefs ->
        prefs[PreferenceKey.FAVORITE_LIST]?.toList()?.map { dataString ->
            Json.decodeFromString<SearchData>(dataString)
        } ?: emptyList()
    }

    suspend fun updateFavoriteList(data: SearchData, isFavorite: Boolean) {
//        dataStore.updateData { prefs ->
//            val currentFavoriteList = dataStore.data.map {
//                prefs[PreferenceKey.FAVORITE_LIST] ?: emptySet()
//            }.first()
//
//
//
//
////            if(isFavorite) {
////                prefs[PreferenceKey.FAVORITE_LIST] = currentFavoriteList + Json.encodeToString(SearchData.serializer(), data)
////            } else {
////                prefs[PreferenceKey.FAVORITE_LIST] = currentFavoriteList - Json.encodeToString(SearchData.serializer(), data)
////            }
//        }
    }
}