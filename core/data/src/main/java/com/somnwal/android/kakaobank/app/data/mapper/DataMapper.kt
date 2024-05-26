package com.somnwal.android.kakaobank.app.data.mapper

import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchImageApiResponse
import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchVideoApiResponse
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchDataType
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult

fun KakaoSearchImageApiResponse.toData() =
    SearchResult(
        isNextPageExist = !this.meta.isEnd,
        resultList = this.documents.map { item ->
            SearchData(
                type = SearchDataType.IMAGE,
                title = item.siteName,
                url = item.imageUrl,
                thumbnailUrl = item.thumbnailUrl,
                datetime = item.datetime,
                isFavorite = false
            )
        }
    )

fun KakaoSearchVideoApiResponse.toData() =
    SearchResult(
        isNextPageExist = !this.meta.isEnd,
        resultList = this.documents.map { item ->
            SearchData(
                type = SearchDataType.VIDEO,
                title = item.title,
                url = item.url,
                thumbnailUrl = item.thumbnailUrl,
                datetime = item.datetime,
                isFavorite = false
            )
        }
    )