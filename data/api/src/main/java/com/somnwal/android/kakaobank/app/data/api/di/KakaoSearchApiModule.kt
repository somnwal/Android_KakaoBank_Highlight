package com.somnwal.android.kakaobank.app.data.api.di

import com.google.gson.JsonNull
import com.somnwal.android.kakaobank.app.data.api.config.ServerConfig
import com.somnwal.android.kakaobank.app.data.api.service.KakaoSearchApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KakaoSearchApiModule {

    @Provides
    fun provideBaseUrl(): String {
        return ServerConfig.KAKAO_API_URL
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                with(chain) {
                    val newRequest = request().newBuilder()
                        .addHeader("Authorization", ServerConfig.KAKAO_API_KEY)
                        .build()
                    proceed(newRequest)
                }
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): KakaoSearchApiService {
        return retrofit.create(KakaoSearchApiService::class.java)
    }

}