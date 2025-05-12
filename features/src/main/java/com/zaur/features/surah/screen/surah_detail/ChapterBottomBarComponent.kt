package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zaur.presentation.ui.ChapterBottomBar
import com.zaur.presentation.ui.QuranColors

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun ChapterBottomBarComponent(
    modifier: Modifier,
    colors: QuranColors,
    isPlaying: Boolean,
    onClickSettings: () -> Unit,
    onClickReciter: (Boolean) -> Unit,
    onClickPlay: () -> Unit
) {
    ChapterBottomBar(
        modifier = modifier,
        colors = colors,
        isPlaying = isPlaying,
        showReciterDialog = onClickReciter,
        showSettings = onClickSettings,
        onClickPlayer = onClickPlay
    )
}
