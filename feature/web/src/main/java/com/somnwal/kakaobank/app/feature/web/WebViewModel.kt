package com.somnwal.kakaobank.app.feature.web

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.somnwal.kakaobank.app.feature.web.navigation.WebRoute
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var url = savedStateHandle.getStateFlow(key = WebRoute.URL_ARG_ID, initialValue = "")

//    val uiState : StateFlow<WebUiState> = WebUiState.Idle
}