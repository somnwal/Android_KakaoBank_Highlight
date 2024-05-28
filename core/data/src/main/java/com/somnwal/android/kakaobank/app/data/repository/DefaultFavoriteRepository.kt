package com.somnwal.android.kakaobank.app.data.repository

import com.somnwal.android.kakaobank.app.core.datastore.datasource.FavoritePreferencesDataSource
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultFavoriteRepository @Inject constructor(
    private val favoriteDataSource: FavoritePreferencesDataSource
) : FavoriteRepository {

    private val _favoriteList: Flow<Set<String>> = favoriteDataSource.favoriteList

    override fun getFavoriteList(): Flow<Set<String>> {
        return _favoriteList.filterNotNull()
    }

    override suspend fun updateFavoriteList(url: String, isFavorite: Boolean) {
        val currentFavoriteList = _favoriteList.first()

        // 활성화 및 비활성화
        favoriteDataSource.updateFavoriteList(
            if(isFavorite) {
                currentFavoriteList + url
            } else {
                currentFavoriteList - url
            }
        )
    }
}