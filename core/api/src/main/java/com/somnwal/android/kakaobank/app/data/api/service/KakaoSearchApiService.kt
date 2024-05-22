package com.somnwal.android.kakaobank.app.data.api.service

import com.somnwal.android.kakaobank.app.data.api.config.ServerConfig
import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchImageApiResponse
import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchVideoApiResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoSearchApiService {

    @GET("/v2/search/image")
    fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int = ServerConfig.KAKAO_API_IMAGE_SEARCH_PAGE_SIZE,
    ) : Observable<Response<KakaoSearchImageApiResponse>>

    @GET("/v2/search/vclip")
    fun searchVideo(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int = ServerConfig.KAKAO_API_VIDEO_SEARCH_PAGE_SIZE,
    ) : Observable<Response<KakaoSearchVideoApiResponse>>
}