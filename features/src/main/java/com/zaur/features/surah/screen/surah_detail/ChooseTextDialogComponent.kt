package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.presentation.ui.QuranColors

@Composable
fun ChooseTextDialogComponent(
    colors: QuranColors,
    state: SurahDetailScreenState,
    isDarkTheme: Boolean,
    quranTextViewModel: QuranTextViewModel,
    themeViewModel: ThemeViewModel,
    surahDetailViewModel: SurahDetailViewModel,
) {
    ChooseTextDialog(
        colors = colors,
        showTextDialog = state.bottomSheetState().showTextBottomSheet(),
        isDarkTheme = isDarkTheme,
        onThemeChange = { themeViewModel.saveTheme(it) },
        fontSizeArabic = state.uiPreferencesState().fontSizeArabic(),
        onFontSizeArabicChange = {
            surahDetailViewModel.fontSizeArabic(it)
            quranTextViewModel.saveFontSizeArabic(it)
        },
        fontSizeRussian = state.uiPreferencesState().fontSizeRussian(),
        onFontSizeRussianChange = {
            surahDetailViewModel.fontSizeRussian(it)
            quranTextViewModel.saveFontSizeRussian(it)
        },
        onDismiss = { surahDetailViewModel.showTextBottomSheet(false) })
}