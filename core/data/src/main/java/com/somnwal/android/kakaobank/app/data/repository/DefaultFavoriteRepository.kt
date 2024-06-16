package com.somnwal.android.kakaobank.app.data.repository

import android.util.Log
import com.google.gson.JsonNull
import com.somnwal.android.kakaobank.app.core.datastore.datasource.FavoritePreferencesDataSource
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val favoriteDataSource: FavoritePreferencesDataSource,
) : FavoriteRepository {

    override val favoriteList: Flow<List<SearchData>> = favoriteDataSource.favoriteList

    override suspend fun updateFavoriteList(data: SearchData, isFavorite: Boolean) {
        favoriteDataSource.updateFavoriteList(data, isFavorite)
    }
}