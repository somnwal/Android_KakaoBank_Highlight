package com.somnwal.android.kakaobank.app.data.api.service

import com.somnwal.android.kakaobank.app.data.api.config.ServerConfig
import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchApiResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET

interface KakaoSearchApiService {

    @GET("/v2/search/web")
    suspend fun search(
        @Field("query") query: String,
        @Field("sort") sort: String,
        @Field("page") page: Int,
        @Field("size") size: Int = ServerConfig.KAKAO_API_SEARCH_PAGE_SIZE,
    ) : Response<KakaoSearchApiResponse>
}