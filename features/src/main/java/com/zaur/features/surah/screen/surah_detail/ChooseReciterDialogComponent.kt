package com.zaur.features.surah.screen.surah_detail

import androidx.compose.runtime.Composable
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors

@Composable
fun ChooseReciterDialogComponent(
    state: SurahDetailScreenState,
    colors: QuranColors,
    viewModel: SurahDetailViewModel,
    audioViewModel: QuranAudioViewModel
) {
    ChooseReciterDialog(
        showDialog = state.reciterState.showReciterDialog,
        colors = colors
    ) { identifier ->
        viewModel.showReciterDialog(false)
        if (!identifier.isNullOrEmpty()) audioViewModel.saveReciter(identifier)
        viewModel.selectedReciter(audioViewModel.getReciterName().toString())
    }
}
