package com.somnwal.android.kakaobank.app.core.domain.usecase

import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import javax.inject.Inject

class UpdateIsFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(data: SearchData, isFavorite: Boolean) {
        favoriteRepository.updateFavoriteList(data, isFavorite)
    }
}