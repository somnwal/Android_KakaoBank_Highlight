package com.somnwal.android.kakaobank.app.data.repository

import android.annotation.SuppressLint
import com.somnwal.android.kakaobank.app.data.api.KakaoSearchApi
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import com.somnwal.android.kakaobank.app.data.mapper.toData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val searchApi: KakaoSearchApi
) : SearchRepository {

    private var isNextImagePageExist = false
    private var isNextVideoPageExist = false

    @SuppressLint("CheckResult")
    override suspend fun getSearchResult(
        query: String,
        sort: String,
        page: Int
    ) : Flow<SearchResult> = flow {
        emit(
            getMergedSearchResult(
                if(page == 1 || isNextImagePageExist) searchApi.searchImage(query, sort, page).body()?.toData() else null,
                if(page == 1 || isNextVideoPageExist) searchApi.searchVideo(query, sort, page).body()?.toData() else null
            )
        )
    }

    private fun getMergedSearchResult(
        imageResult: SearchResult? = null,
        videoResult: SearchResult? = null
    ) : SearchResult {
        isNextImagePageExist = imageResult?.isNextPageExist ?: false
        isNextVideoPageExist = videoResult?.isNextPageExist ?: false

        val imageResultList = imageResult?.resultList ?: emptyList()
        val videoResultList = videoResult?.resultList ?: emptyList()

        return SearchResult(
            isNextPageExist = (isNextImagePageExist || isNextVideoPageExist),
            resultList = (imageResultList + videoResultList).sortedByDescending {
                it.datetime
            }
        )
    }
}