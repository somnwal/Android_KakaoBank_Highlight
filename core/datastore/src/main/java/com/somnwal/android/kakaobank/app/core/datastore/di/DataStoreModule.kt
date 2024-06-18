package com.somnwal.android.kakaobank.app.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.somnwal.android.kakaobank.app.core.datastore.util.KakaoPreferencesSerializer
import com.somnwal.kakaobank.app.core.datastore.KakaoPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val KAKAO_DATASTORE_FILENAME = "kakao_preferences.pb"
    private val Context.kakaoDataStore : DataStore<KakaoPreferences> by dataStore(
        fileName = KAKAO_DATASTORE_FILENAME,
        serializer = KakaoPreferencesSerializer
    )

    @Provides
    @Singleton
    fun provideKakaoDataStore(
        @ApplicationContext context: Context,
    ): DataStore<KakaoPreferences> = context.kakaoDataStore
}