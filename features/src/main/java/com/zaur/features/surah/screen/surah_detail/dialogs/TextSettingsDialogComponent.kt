package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import com.zaur.features.surah.screen.surah_detail.SurahDetailDependencies
import com.zaur.features.surah.screen.surah_detail.SurahDetailUiData
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun TextSettingsDialogComponent(
    colors: QuranColors,
    uiData: SurahDetailUiData,
    deps: SurahDetailDependencies,
) {
    with(uiData) {
        with(deps) {
            TextSettingsDialog(
                colors = colors,
                showTextDialog = surahDetailState().bottomSheetState().showTextBottomSheet(),
                isDarkTheme = isDarkTheme(),
                isSurahMode = isSurahMode(),
                onThemeChange = { themeViewModel().saveTheme(it) },
                fontSizeArabic = surahDetailState().uiPreferencesState().fontSizeArabic(),
                onFontSizeArabicChange = {
                    surahDetailViewModel().fontSizeArabic(it)
                    quranTextViewModel().saveFontSizeArabic(it)
                },
                fontSizeRussian = surahDetailState().uiPreferencesState().fontSizeRussian(),
                onFontSizeRussianChange = {
                    surahDetailViewModel().fontSizeRussian(it)
                    quranTextViewModel().saveFontSizeRussian(it)
                },
                onPageModeClicked = {
                    surahDetailViewModel().showPageMode(true)
                    screenContentViewModel().fetchSurahMode()
                },
                onSurahModeClicked = {
                    surahDetailViewModel().showSurahMode(true)
                    screenContentViewModel().fetchSurahMode()
                },
                onDismiss = { surahDetailViewModel().showTextBottomSheet(false) })
        }
    }
}