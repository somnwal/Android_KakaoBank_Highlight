package com.somnwal.highlight.viewmodel

import androidx.lifecycle.ViewModel
import com.somnwal.android.kakao.highlight.api.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api: ApiClient
) : ViewModel() {

}