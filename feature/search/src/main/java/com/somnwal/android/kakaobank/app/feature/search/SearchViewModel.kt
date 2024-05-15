package com.somnwal.android.kakaobank.app.feature.search

import com.somnwal.android.kakaobank.app.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val repository: SearchRepository
) {
    fun getTest() = repository.getTest()
}