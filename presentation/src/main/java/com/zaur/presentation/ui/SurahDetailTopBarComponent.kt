package com.zaur.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zaur.presentation.R

/**
 * @author Zaur
 * @since 2025-05-12
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailTopBarComponent(
    surahName: String,
    modifier: Modifier = Modifier,
    colors: QuranColors,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    val iconModifier = Modifier.size(26.dp)

    Column(modifier = modifier) {
        TopAppBar(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            title = { Text(surahName) },
            navigationIcon = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.hamburger_menu),
                        contentDescription = "Меню",
                        tint = colors.iconColorForTop
                    )
                }
            },
            actions = {
                IconButton(onClick = { onSearchClick() }) {
                    Icon(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.search),
                        contentDescription = "Поиск",
                        tint = colors.iconColorForTop
                    )
                }
            },
        )

        // Этот Spacer будет ровно под TopAppBar
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(colors.divider)
        )
    }
}