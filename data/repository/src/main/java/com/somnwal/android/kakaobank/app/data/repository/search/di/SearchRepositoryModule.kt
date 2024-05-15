package com.somnwal.android.kakaobank.app.data.repository.search.di

import com.somnwal.android.kakaobank.app.data.repository.search.DefaultSearchRepository
import com.somnwal.android.kakaobank.app.data.repository.search.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {

    @Binds
    abstract fun bindSearchRepository(repository: DefaultSearchRepository) : SearchRepository
}