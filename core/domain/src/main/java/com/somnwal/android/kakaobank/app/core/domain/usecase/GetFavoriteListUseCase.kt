package com.somnwal.android.kakaobank.app.core.domain.usecase

import com.somnwal.kakaobank.app.core.data.repository.api.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() : Flow<Set<String>> =
        favoriteRepository.getFavoriteList()
}