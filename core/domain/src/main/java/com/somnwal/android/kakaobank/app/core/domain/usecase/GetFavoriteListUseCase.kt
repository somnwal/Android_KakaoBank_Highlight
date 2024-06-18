package com.somnwal.android.kakaobank.app.core.domain.usecase

import android.util.Log
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() : Flow<List<SearchData>> = favoriteRepository.favoriteList.map { favoriteList ->
        favoriteList.map {
            it.apply {
                isFavorite = true
            }
        }
    }
}