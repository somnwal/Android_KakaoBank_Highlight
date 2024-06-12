package com.somnwal.android.kakaobank.app.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.somnwal.android.kakaobank.app.core.domain.usecase.GetFavoriteListUseCase
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import java.lang.Thread.State
import javax.inject.Inject

@HiltViewModel
class SearchDetailViewModel @Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var searchData = savedStateHandle.getStateFlow(key = SEARCH_DATA, initialValue = null)

//    @OptIn(ExperimentalCoroutinesApi::class)
//    val isFavoriteState : StateFlow<Boolean> =
//        getFavoriteListUseCase().flatMapLatest { favoriteList ->
//            if(searchData == null) {
//                favoriteList.filter {
//                    it == searchData
//                }.firstOrNull().isFavorite ?: false
//            } else {
//                false
//            }
//        }.state
}