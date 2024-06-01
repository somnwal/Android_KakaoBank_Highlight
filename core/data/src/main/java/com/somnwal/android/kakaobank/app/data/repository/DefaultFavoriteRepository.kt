package com.somnwal.android.kakaobank.app.data.repository

import com.google.gson.JsonNull
import com.somnwal.android.kakaobank.app.core.datastore.datasource.FavoritePreferencesDataSource
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val favoriteDataSource: FavoritePreferencesDataSource
) : FavoriteRepository {

    private val _favoriteList: Flow<Set<String>> = favoriteDataSource.favoriteList

    override fun getFavoriteList(): Flow<List<SearchData>> =
        _favoriteList.map { favoriteSet ->
            favoriteSet.toList().map { dataString ->
                Json.decodeFromString<SearchData>(dataString)
            }
        }

    override suspend fun updateFavoriteList(data: SearchData, isFavorite: Boolean) {
        val currentFavoriteList = _favoriteList.first()

        // 활성화 및 비활성화
        favoriteDataSource.updateFavoriteList(
            if(isFavorite) {
                currentFavoriteList + data.toString()
            } else {
                currentFavoriteList - data.toString()
            }
        )
    }
}