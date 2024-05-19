package com.somnwal.android.kakaobank.app.data.api.service

import com.somnwal.android.kakaobank.app.data.api.config.ServerConfig
import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchApiResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoSearchApiService {

    @GET("/v2/search/image")
    suspend fun search(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int = ServerConfig.KAKAO_API_SEARCH_PAGE_SIZE,
    ) : Response<KakaoSearchApiResponse>
}