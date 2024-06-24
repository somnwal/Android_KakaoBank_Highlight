package com.somnwal.kakaobank.app.feature.web

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.somnwal.kakaobank.app.core.designsystem.component.LoadingBar

@Composable
internal fun WebRoute(
    padding: PaddingValues,
    onShowErrorSnackbar: (Throwable?) -> Unit,
    viewModel: WebViewModel = hiltViewModel()
) {
    val url by viewModel.url.collectAsState()

    var loading by remember {
        mutableStateOf(false)
    }

    AndroidView(
        factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)

                        loading = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)

                        loading = false
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)

                        onShowErrorSnackbar(Throwable("오류가 발생했습니다."))
                    }
                }
            }
        },
        update = {
            it.loadUrl(url)
        }
    )

    if(loading) {
        LoadingBar()
    }
}