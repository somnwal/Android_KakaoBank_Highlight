package com.somnwal.android.kakaobank.app.data.di

import com.somnwal.android.kakaobank.app.data.repository.DefaultSearchRepository
import com.somnwal.android.kakaobank.app.data.repository.DefaultSettingsRepository
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import com.somnwal.kakaobank.app.core.data.repository.api.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class KakaoDataModule {

    @Binds
    abstract fun bindsSettingsRepository(
        repository: DefaultSettingsRepository
    ): SettingsRepository

    @Binds
    abstract fun bindsSearchRepository(
        repository: DefaultSearchRepository
    ): SearchRepository
}