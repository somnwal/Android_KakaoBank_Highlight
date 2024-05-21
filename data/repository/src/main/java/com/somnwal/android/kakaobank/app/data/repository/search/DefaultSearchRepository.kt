package com.somnwal.android.kakaobank.app.data.repository.search

import android.annotation.SuppressLint
import com.somnwal.android.kakaobank.app.data.api.service.KakaoSearchApiService
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.android.kakaobank.app.data.repository.search.mapper.convert
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val apiService: KakaoSearchApiService
) : SearchRepository {

    private var isNextImagePageExist = false
    private var isNextVideoPageExist = false

    @SuppressLint("CheckResult")
    override suspend fun getSearchResult(
        query: String,
        sort: String,
        page: Int
    ) : Observable<SearchResult> {
        val imageResult = if(page == 1 || isNextImagePageExist) {
            apiService.searchImage(query, sort, page)
        } else {
            Observable.empty()
        }

        val videoResult = if(page == 1 || isNextVideoPageExist) {
            apiService.searchVideo(query, sort, page)
        } else {
            Observable.empty()
        }

        return Observable.zip(
            imageResult,
            videoResult
        ) { imageResponse, videoResponse ->
            val convertedImageResult = imageResponse.body()?.convert() ?: SearchResult(false, listOf())
            val convertedVideoResult = videoResponse.body()?.convert() ?: SearchResult(false, listOf())

            isNextImagePageExist = convertedImageResult.isNextPageExist
            isNextVideoPageExist = convertedVideoResult.isNextPageExist

            SearchResult(
                isNextPageExist = convertedImageResult.isNextPageExist || convertedVideoResult.isNextPageExist,
                resultList = (convertedImageResult.resultList + convertedVideoResult.resultList)
            )
        }

    }
}