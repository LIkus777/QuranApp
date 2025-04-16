package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors

@Composable
fun SettingsBottomSheetComponent(
    state: SurahDetailScreenState, colors: QuranColors, viewModel: SurahDetailViewModel
) {
    if (state.bottomSheetState.showSettingsBottomSheet) {
        SettingsBottomSheet(
            showSheet = true,
            colors = colors,
            selectedReciter = state.reciterState.currentReciter,
            showReciterDialog = { viewModel.showReciterDialog(it) },
            showArabic = state.uiPreferences.showArabic,
            onShowArabicChange = { viewModel.showArabic(it) },
            showRussian = state.uiPreferences.showRussian,
            onShowRussianChange = { viewModel.showRussian(it) },
            onDismiss = { viewModel.showSettingsBottomSheet(false) })
    }
}
