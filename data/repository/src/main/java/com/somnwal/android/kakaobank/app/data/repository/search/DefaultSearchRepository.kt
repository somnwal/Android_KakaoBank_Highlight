package com.somnwal.android.kakaobank.app.data.repository.search

import android.util.Log
import com.somnwal.android.kakaobank.app.data.api.service.KakaoSearchApiService
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import com.somnwal.android.kakaobank.app.data.repository.search.mapper.convert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.logging.Logger
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val apiService: KakaoSearchApiService
) : SearchRepository {

    override suspend fun getSearchResult(query: String, sort: String, page: Int) : SearchResult {
        val data = apiService.search(query, sort, page)

        return when(data.isSuccessful) {
            true -> {
                data.body()?.convert() ?: SearchResult(
                    isNextPageExist = false,
                    resultList = listOf()
                )
            }

            else -> {
                SearchResult(
                    isNextPageExist = false,
                    resultList = listOf()
                )
            }
        }
    }
}