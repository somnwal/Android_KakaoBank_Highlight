package com.somnwal.android.kakao.highlight.api

class ApiClient {

    companion object {
        @Volatile
        private var instance: ApiClient? = null
        private val LOCK = Any()

        fun getInstance() = instance ?: synchronized(LOCK) {
            instance ?: ApiClient().also { instance = it }
        }
    }
}