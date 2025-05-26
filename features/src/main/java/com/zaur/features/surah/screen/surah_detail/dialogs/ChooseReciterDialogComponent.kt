package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.ui_state.aqc.SurahDetailScreenState

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun ChooseReciterDialogComponent(
    state: SurahDetailScreenState,
    colors: QuranColors,
    viewModel: SurahDetailViewModel,
    audioViewModel: SurahPlayerViewModel,
) {
    ChooseReciterDialog(
        showDialog = state.reciterState().showReciterDialog(), colors = colors
    ) { identifier ->
        viewModel.showReciterDialog(false)
        if (!identifier.isNullOrEmpty()) audioViewModel.saveReciter(identifier)
        viewModel.selectedReciter(
            audioViewModel.getReciter().toString(),
            audioViewModel.getReciterName().toString()
        )
    }
}
