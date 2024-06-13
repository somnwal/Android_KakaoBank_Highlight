package com.somnwal.android.kakaobank.app.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.somnwal.android.kakaobank.app.data.api.KakaoSearchApi
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import com.somnwal.android.kakaobank.app.data.mapper.toData
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val searchApi: KakaoSearchApi
) : SearchRepository {

    private var savedSearchDataList = listOf<SearchData>()

    private var isNextImagePageExist = false
    private var isNextVideoPageExist = false

    override fun getSearchResult(
        query: String,
        page: Int,
        sort: String
    ) : Flow<List<SearchData>> {
        Log.d("로그","Call getSearchResult >>>")

        if(page == 1) savedSearchDataList = listOf()

        return flowOf(
            getMergedSearchResult(
                if(page == 1 || isNextImagePageExist) searchApi.searchImage(query, page, sort).body()?.toData() else null,
                if(page == 1 || isNextVideoPageExist) searchApi.searchVideo(query, page, sort).body()?.toData() else null
            )
        )
    }

    private fun getMergedSearchResult(
        imageResult: SearchResult? = null,
        videoResult: SearchResult? = null
    ) : List<SearchData> {
        isNextImagePageExist = imageResult?.isNextPageExist ?: false
        isNextVideoPageExist = videoResult?.isNextPageExist ?: false

        val imageResultList = imageResult?.resultList ?: emptyList()
        val videoResultList = videoResult?.resultList ?: emptyList()

        val mergedSearchResult = (savedSearchDataList + imageResultList + videoResultList)

        Log.d("로그","mergedSearchResult : ${mergedSearchResult}")

        return mergedSearchResult.sortedByDescending {
            it.datetime
        }
    }
}