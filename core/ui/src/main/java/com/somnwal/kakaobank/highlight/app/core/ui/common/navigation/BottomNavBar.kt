package com.somnwal.kakaobank.highlight.app.core.ui.common.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(
    modifier: Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            thickness = 1.dp
        )

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth(),
            content = content
        )
    }
}

@Composable
fun RowScope.BottomNavBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    label: String
) {
    NavigationBarItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        icon = {
            val icon = if(selected) {
                selectedIcon
            } else {
                unselectedIcon
            }

            Icon(
                imageVector = icon,
                contentDescription = "icon"
            )
        },
        label = {
            Text(text = label)
        }
    )
}

