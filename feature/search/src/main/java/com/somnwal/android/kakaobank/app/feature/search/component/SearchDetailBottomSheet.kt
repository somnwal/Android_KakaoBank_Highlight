package com.somnwal.android.kakaobank.app.feature.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.somnwal.android.kakaobank.app.data.model.search.SearchData
import com.somnwal.android.kakaobank.app.data.model.search.SearchDataType
import com.somnwal.kakaobank.app.core.designsystem.component.AppIcons
import com.somnwal.kakaobank.app.core.designsystem.theme.KakaoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchDetailBottomSheet(
    modifier: Modifier = Modifier,
    data: SearchData,
    onUpdateIsFavorite: (SearchData) -> Unit,
    closeSheet: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = closeSheet,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(
                    top = 24.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = data.title,
                textAlign = TextAlign.Center,
                fontSize = TextUnit.Unspecified
            )

            Spacer(modifier = Modifier.size(16.dp))

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(1f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.url)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "이미지"
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(0.75f)
                        .padding(
                            top = 6.dp
                        )
                        .wrapContentHeight()
                ) {
                    Text(text = data.datetime)
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .weight(0.25f)
                        .wrapContentHeight()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(36.dp)
                            .padding(6.dp)
                            .clickable(
                                onClick = {
                                    onUpdateIsFavorite(data)
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
}

@Preview(
    widthDp = 300,
    heightDp = 200,
    showBackground = true
)
@Composable
internal fun SearchDetailBottomSheetPreivew() {
    KakaoTheme {
        SearchDetailBottomSheet(
            data = SearchData(
                type =  SearchDataType.IMAGE,
                title = "이미지",
                thumbnailUrl = "",
                url = "",
                datetime = "9999-12-31"
            ),
            onUpdateIsFavorite = { },
            closeSheet = { }
        )
    }
}