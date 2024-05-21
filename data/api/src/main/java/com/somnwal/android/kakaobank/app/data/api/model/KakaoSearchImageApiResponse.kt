package com.somnwal.android.kakaobank.app.data.api.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class KakaoSearchImageApiResponse (
    @SerializedName("meta")
    val meta: KakaoSearchImageApiMetaData,

    @SerializedName("documents")
    val documents: List<KakaoSearchImageApiDocumentsData>
)

data class KakaoSearchImageApiMetaData (
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("is_end")
    val isEnd: Boolean
)

data class KakaoSearchImageApiDocumentsData (
    @SerializedName("collection")
    val collection: String,

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("display_sitename")
    val siteName: String,

    @SerializedName("doc_url")
    val docUrl: String,

    @SerializedName("datetime")
    val datetime: Date,
)