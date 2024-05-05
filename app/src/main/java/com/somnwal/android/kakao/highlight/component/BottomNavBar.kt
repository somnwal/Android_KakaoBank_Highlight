package com.somnwal.android.kakao.highlight.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.somnwal.android.kakao.highlight.config.Consts
import com.somnwal.android.kakao.highlight.config.NavScreen

@Composable
fun BottomNavBar(
    onNavItemSelected: (navItem: NavScreen) -> Unit,
    selectedItem: NavDestination?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp)
            .background(Color.White)
    ) {
        for (item in Consts.NAV_MENU) {
            val isSelected = selectedItem?.route == item.route

            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(5.dp)
                    .weight(1f)
                    .clickable { onNavItemSelected(item) },
                colorFilter = if (isSelected) ColorFilter.tint(Color.Red)
                else ColorFilter.tint(Color.Gray)
            )
        }
    }
}