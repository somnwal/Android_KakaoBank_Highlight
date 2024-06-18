package com.somnwal.android.kakaobank.app.core.datastore.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.google.protobuf.kotlin.toByteStringUtf8
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.UserData
import com.somnwal.kakaobank.app.core.datastore.KakaoPreferences
import com.somnwal.kakaobank.app.core.datastore.copy
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.encode
import okio.Utf8
import javax.inject.Inject

class KakaoPreferencesDataSource @Inject constructor(
    private val kakaoPreferences: DataStore<KakaoPreferences>
) {
    val prefs = kakaoPreferences.data
        .map {
            UserData(
                isDarkTheme = it.isDarkTheme,
                favoriteList = it.favoriteListMap.values.toMutableSet()
            )
        }

    suspend fun updateFavoriteList(key: String, data: String, isFavorite: Boolean) {
        try {
            kakaoPreferences.updateData {
                it.copy {
                    if(isFavorite) {
                        favoriteList.put(key, data)
                    } else {
                        favoriteList.remove(key)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        try {
            kakaoPreferences.updateData {
                it.copy {
                    this.isDarkTheme = isDarkTheme
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}