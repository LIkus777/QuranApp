package com.zaur.presentation.ui

import androidx.compose.runtime.Composable

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun ChapterBottomBarComponent(
    colors: QuranColors,
    onClickSettings: () -> Unit,
    onClickReciter: (Boolean) -> Unit,
    onClickPlay: () -> Unit
) {
    SurahDetailBottomAppBar(
        colors = colors,
        showReciterDialog = onClickReciter,
        showSettings = onClickSettings,
        onClickPlayer = onClickPlay
    )
}
