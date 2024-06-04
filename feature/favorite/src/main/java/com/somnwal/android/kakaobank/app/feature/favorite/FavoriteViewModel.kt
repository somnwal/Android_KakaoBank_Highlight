package com.somnwal.android.kakaobank.app.feature.favorite

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.somnwal.android.kakaobank.app.core.domain.usecase.GetFavoriteListUseCase
import com.somnwal.android.kakaobank.app.core.domain.usecase.UpdateIsFavoriteUseCase
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.feature.favorite.state.FavoriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val updateIsFavoriteUseCase: UpdateIsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteUiState>(FavoriteUiState.Idle)

    val uiState: StateFlow<FavoriteUiState>
        get() = _uiState.asStateFlow()

    private val _errorFlow = MutableSharedFlow<Throwable>()

    val errorFlow
        get() = _errorFlow.asSharedFlow()

    fun getFavoriteList() {
        viewModelScope.launch {
            getFavoriteListUseCase()
                .collectLatest { favoriteList ->
                    _uiState.value = FavoriteUiState.Success(
                        data = favoriteList.toPersistentList()
                    )
                }
        }
    }

    fun updateIsFavorite(searchData: SearchData) {
        viewModelScope.launch {
            searchData.isFavorite = !searchData.isFavorite
            updateIsFavoriteUseCase(searchData, searchData.isFavorite)
        }
    }
}