package com.somnwal.kakaobank.app.core.data.repository.api

import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteList() : Flow<Set<String>>

    suspend fun updateFavoriteList(url: String, isFavorite: Boolean)
}