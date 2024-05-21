package com.somnwal.android.kakaobank.app.data.repository.search

import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface SearchRepository {
    suspend fun getSearchResult(query: String, sort: String, page: Int) : Observable<SearchResult>
}
