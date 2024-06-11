package com.somnwal.kakaobank.app.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    uri: String
) {
    val localContext = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(localContext).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxHeight(0.5f),
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        }
    )
}