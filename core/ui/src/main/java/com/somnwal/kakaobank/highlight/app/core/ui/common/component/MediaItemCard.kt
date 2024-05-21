package com.somnwal.kakaobank.highlight.app.core.ui.common.component

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchDataType
import com.somnwal.kakaobank.highlight.app.core.ui.icon.AppIcons
import com.somnwal.kakaobank.highlight.app.core.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
@Composable
fun MediaItemCard(
    modifier: Modifier = Modifier,
    data: SearchData,
    isExpanded: Boolean = false
) {
    val localDensity = LocalDensity.current

    var dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var formattedDateText = dateFormatter.format(data.datetime)

    var imageUrl by remember { mutableStateOf(data.thumbnailUrl) }
    var imageLoading by remember { mutableStateOf(false) }

    var imageDisplayRatio by rememberSaveable { mutableFloatStateOf(0.3f) }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            imageDisplayRatio = 1f
            imageUrl = data.url
        } else {
            imageDisplayRatio = 0.3f
            imageUrl = data.thumbnailUrl
        }
    }

    Card(
        modifier = modifier
            .padding(4.dp)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(
                    onClick = {

                    }
                )
        ) {

            val imageSize = remember(imageDisplayRatio) {
                with(localDensity) {
                    Size(
                        (maxWidth * imageDisplayRatio).toPx().toInt(),
                        (maxWidth * imageDisplayRatio).toPx().toInt()
                    )
                }
            }

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth(imageDisplayRatio)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.thumbnailUrl)
                    .size(imageSize)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "이미지"
            )

            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .padding(6.dp)
                    .align(Alignment.BottomStart),
                imageVector = if(data.type == SearchDataType.IMAGE) {
                    AppIcons.ICON_IMAGE_OUTLINED
                } else {
                    AppIcons.ICON_VIDEO_OUTLINED
                },
                tint = Color.White,
                contentDescription = "파일 형식"
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = maxWidth * imageDisplayRatio)
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight(),
                    text = data.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    modifier = Modifier
                        .wrapContentHeight(),
                    text = formattedDateText
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .wrapContentHeight()
            ){
                Icon(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(6.dp)
                        .clickable(
                            onClick = {

                            }
                        ),
                    imageVector = if(data.isFavorite) {
                        AppIcons.ICON_FAVORITE_FILLED
                    } else {
                        AppIcons.ICON_FAVORITE_OUTLINED
                    },
                    tint = Color.Red,
                    contentDescription = "좋아요"
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 420,
    heightDp = 120
)
@Composable
fun MediaItemImageCardPreview() {
    AppTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
           MediaItemCard(
               data = SearchData(
                   type = SearchDataType.IMAGE,
                   title = "이미지",
                   url = "https://avatars.githubusercontent.com/u/90139018?v=4",
                   thumbnailUrl = "https://avatars.githubusercontent.com/u/90139018?v=4",
                   datetime = Date(),
                   isFavorite = false,
               ),
           )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 420,
    heightDp = 120
)
@Composable
fun MediaItemVideoCardPreview() {
    AppTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MediaItemCard(
                data = SearchData(
                    type = SearchDataType.VIDEO,
                    title = "동영상",
                    url = "https://avatars.githubusercontent.com/u/90139018?v=4",
                    thumbnailUrl = "https://avatars.githubusercontent.com/u/90139018?v=4",
                    datetime = Date(),
                    isFavorite = false,
                ),
            )
        }
    }
}