package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun SettingsDialogComponent(
    state: SurahDetailScreenState, colors: QuranColors, viewModel: SurahDetailViewModel
) {
    if (state.bottomSheetState().showSettingsBottomSheet()) {
        SettingsDialog(
            showSheet = true,
            colors = colors,
            selectedReciter = state.reciterState().currentReciterName(),
            showReciterDialog = { viewModel.showReciterDialog(it) },
            showArabic = state.uiPreferencesState().showArabic(),
            onShowArabicChange = { viewModel.showArabic(it) },
            showRussian = state.uiPreferencesState().showRussian(),
            onShowRussianChange = { viewModel.showRussian(it) },
            onDismiss = { viewModel.showSettingsBottomSheet(false) })
    }
}
