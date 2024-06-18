package com.somnwal.android.kakaobank.app.data.repository

import com.somnwal.android.kakaobank.app.core.datastore.datasource.KakaoPreferencesDataSource
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val kakaoPreferencesDataSource: KakaoPreferencesDataSource
) : FavoriteRepository {

    override val favoriteList: Flow<List<SearchData>> =
        kakaoPreferencesDataSource.prefs.map { prefs ->
            prefs.favoriteList.toList().map { data ->
                Json.decodeFromString<SearchData>(data)
            }.sortedByDescending {
                it.datetime
            }
        }

    override suspend fun updateFavoriteList(data: SearchData, isFavorite: Boolean) {
        kakaoPreferencesDataSource.updateFavoriteList(
            key = data.url,
            data = Json.encodeToString(SearchData.serializer(), data),
            isFavorite = isFavorite
        )
    }
}