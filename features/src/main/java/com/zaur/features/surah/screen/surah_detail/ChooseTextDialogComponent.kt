package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors

@Composable
fun ChooseTextDialogComponent(
    colors: QuranColors,
    state: SurahDetailScreenState,
    isDarkTheme: Boolean,
    viewModel: SurahDetailViewModel
) {
    ChooseTextDialog(
        colors = colors,
        showTextDialog = state.bottomSheetState.showTextBottomSheet,
        isDarkTheme = isDarkTheme,
        fontSizeArabic = state.uiPreferences.fontSizeArabic,
        onFontSizeArabicChange = { viewModel.fontSizeArabic(it) },
        fontSizeRussian = state.uiPreferences.fontSizeRussian,
        onFontSizeRussianChange = { viewModel.fontSizeRussian(it) },
        onThemeChange = {},
        onDismiss = { viewModel.showTextBottomSheet(false) })
}
