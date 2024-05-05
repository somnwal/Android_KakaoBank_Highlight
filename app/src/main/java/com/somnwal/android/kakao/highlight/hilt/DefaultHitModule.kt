package com.somnwal.android.kakao.highlight.hilt

import com.somnwal.android.kakao.highlight.api.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DefaultHitModule {
    @Provides
    fun provideApi(): ApiClient = ApiClient.getInstance()
}