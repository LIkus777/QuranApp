package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SurahDetailScreenContent(
    surahName: String,
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    onMenuClick: () -> Unit,
) {
    val uiData = rememberSurahDetailUiData(chapterNumber, deps)
    val listState = rememberLazyListState()
    val colors = if (uiData.isDarkTheme()) DarkThemeColors else LightThemeColors

    // Эффекты
    SurahDetailEffects(
        chapterNumber = chapterNumber, deps = deps, uiData = uiData
    )

    // Контент
    ScreenContent(
        chapterNumber = chapterNumber,
        listState = listState,
        deps = deps,
        uiData = uiData,
        colors = colors,
        surahName = surahName,
        onMenuClick = onMenuClick
    )

    // Диалоги
    SettingsBottomSheetComponent(uiData.surahDetailState(), colors, deps.surahDetailViewModel())
    ChooseTextDialogComponent(colors, uiData, deps)
    ChooseReciterDialogComponent(
        uiData.surahDetailState(),
        colors,
        deps.surahDetailViewModel(),
        deps.quranAudioViewModel()
    )
}