package com.somnwal.android.kakaobank.app.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.somnwal.android.kakaobank.app.data.api.KakaoSearchApi
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.kakaobank.app.core.data.repository.api.SearchRepository
import com.somnwal.android.kakaobank.app.data.mapper.toData
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val searchApi: KakaoSearchApi
) : SearchRepository {

    private var savedSearchDataList = listOf<SearchData>()

    private var isNextImagePageExist = true
    private var isNextVideoPageExist = true

    override fun getSearchResult(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<SearchData>> = flow {

        if(page == 1) {
            savedSearchDataList = listOf()
        }

        val imageResult = if(page == 1 || isNextImagePageExist) {
            searchApi.searchImage(query, page, sort).body()?.toData()
        } else {
            null
        }

        val videoResult = if(page == 1 || isNextVideoPageExist) {
            searchApi.searchVideo(query, page, sort).body()?.toData()
        } else {
            null
        }

        isNextImagePageExist = imageResult?.isNextPageExist ?: false
        isNextVideoPageExist = videoResult?.isNextPageExist ?: false

        val imageResultList = imageResult?.resultList ?: emptyList()
        val videoResultList = videoResult?.resultList ?: emptyList()

        val mergedSearchResult = (savedSearchDataList + imageResultList + videoResultList)
        savedSearchDataList = mergedSearchResult

        Log.d("로그", "savedSearchDataList : ${savedSearchDataList}")

        emit(
            mergedSearchResult.sortedByDescending {
                it.datetime
            }
        )
    }.flowOn(Dispatchers.IO)
}