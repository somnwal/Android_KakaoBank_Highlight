package com.somnwal.kakaobank.app.core.data.repository.api

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteList() : Flow<List<SearchData>>

    suspend fun updateFavoriteList(data: SearchData, isFavorite: Boolean)
}