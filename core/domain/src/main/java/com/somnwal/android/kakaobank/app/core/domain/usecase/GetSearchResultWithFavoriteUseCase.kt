package com.somnwal.android.kakaobank.app.core.domain.usecase

import android.util.Log
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchResultWithFavoriteUseCase @Inject constructor(
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
) {
    operator fun invoke(
        query: String,
        page: Int,
        sort: String
    ): Flow<List<SearchData>> = channelFlow {

        Log.d("로그", "Call GetSearchResultWithFavoriteUseCase")

        var searchList: MutableList<SearchData> = mutableListOf()
        var favoriteList: MutableList<SearchData> = mutableListOf()

        withContext(Dispatchers.IO) {
            launch {
                getSearchResultUseCase(query, page, sort)
                    .collectLatest { search ->
                        Log.d("로그", "searchList 2 : $search")
                        searchList.addAll(search)
                    }
            }

            launch {
                getFavoriteListUseCase()
                    .collectLatest { favorite ->
                        Log.d("로그", "favoriteList 2 : $favorite")
                        favoriteList.addAll(favorite)
                    }
            }
        }

        Log.d("로그", "searchList : $searchList")
        Log.d("로그", "favoriteList : $favoriteList")


        val searchListWithFavoriteList = searchList.map {
            it.apply {
                isFavorite = favoriteList.contains(it)
            }
        }

        send(searchListWithFavoriteList)
    }
}