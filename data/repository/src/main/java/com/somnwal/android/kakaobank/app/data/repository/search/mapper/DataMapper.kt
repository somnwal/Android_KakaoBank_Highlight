package com.somnwal.android.kakaobank.app.data.repository.search.mapper

import com.somnwal.android.kakaobank.app.data.api.model.KakaoSearchApiResponse
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchDataType

fun KakaoSearchApiResponse.convert() =
    this.documents.map { item ->
        SearchData(
            type = SearchDataType.IMAGE,
            title = item.siteName,
            imageUrl = item.imageUrl,
            thumbnailUrl = item.thumbnailUrl,
            docUrl = item.docUrl,
            timestamp = item.datetime
        )
    }