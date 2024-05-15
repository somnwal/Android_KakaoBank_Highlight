package com.somnwal.android.kakaobank.app.data.api.di

import com.somnwal.android.kakaobank.app.data.api.config.ServerConfig
import com.somnwal.android.kakaobank.app.data.api.service.KakaoSearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoSearchApiModule {

    @Provides
    fun provideBaseUrl() = ServerConfig.KAKAO_API_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create(KakaoSearchApiService::class.java)

}