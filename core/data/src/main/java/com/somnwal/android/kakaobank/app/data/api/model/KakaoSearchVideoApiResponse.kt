package com.somnwal.android.kakaobank.app.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class KakaoSearchVideoApiResponse (
    @SerializedName("meta")
    val meta: KakaoSearchImageApiMetaData,

    @SerializedName("documents")
    val documents: List<KakaoSearchVideoApiDocumentsData>
)

data class KakaoSearchVideoApiMetaData (
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("is_end")
    val isEnd: Boolean
)

data class KakaoSearchVideoApiDocumentsData (
    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("datetime")
    val datetime: Date,

    @SerializedName("play_time")
    val playTime: Int,

    @SerializedName("thumbnail")
    val thumbnailUrl: String,

    @SerializedName("author")
    val author: String,
)