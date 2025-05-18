package com.zaur.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun ChapterBottomBarComponent(
    colors: QuranColors,
    isPlaying: Boolean,
    onClickSettings: () -> Unit,
    onClickReciter: (Boolean) -> Unit,
    onClickPlay: () -> Unit
) {
    ChapterBottomBar(
        colors = colors,
        isPlaying = isPlaying,
        showReciterDialog = onClickReciter,
        showSettings = onClickSettings,
        onClickPlayer = onClickPlay
    )
}
