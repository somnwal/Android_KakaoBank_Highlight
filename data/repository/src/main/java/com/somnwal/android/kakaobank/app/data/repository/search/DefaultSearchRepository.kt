package com.somnwal.android.kakaobank.app.data.repository.search

import com.somnwal.android.kakaobank.app.data.api.service.KakaoSearchApiService
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.repository.search.mapper.convert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DefaultSearchRepository @Inject constructor(
    private val apiService: KakaoSearchApiService
) : SearchRepository {
    private val items: MutableStateFlow<List<SearchData>> = MutableStateFlow(listOf())
    override val observeSearchDataList: Flow<List<SearchData>> = items

    override suspend fun search(query: String, sort: String, page: Int) {
        val data = apiService.search(query, sort, page)

        when(data.isSuccessful) {
            true -> {

                val newItems = data.body()?.convert()


                newItems?.let {
                    // TODO SharedPrefs 에서 불러오기

                    items.emit(newItems)
                }
            }
            else -> {

            }
        }
    }
}