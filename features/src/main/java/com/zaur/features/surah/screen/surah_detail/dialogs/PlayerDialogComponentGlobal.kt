package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel
import com.zaur.presentation.ui.QuranColors


/**
 * @author Zaur
 * @since 26.05.2025
 */

@Composable
fun PlayerDialogComponentGlobal(
    surahDetailViewModel: SurahDetailViewModel,
    surahPlayerViewModel: SurahPlayerViewModel,
    colors: QuranColors,
    contentPadding: PaddingValues = PaddingValues()
) {
    val state = surahDetailViewModel.surahDetailState().collectAsState().value
    val audioState = state.audioPlayerState()

    if (state.bottomSheetState().showPlayerBottomSheet()) {
        PlayerDialog(
            contentPadding,
            soundDuration = audioState.duration(),
            soundPosition = audioState.position(),
            colors = colors,
            surahName = audioState.surahName(),
            ayahNumber = audioState.currentAyah(),
            surahNumber = audioState.currentSurahNumber(),
            reciterName = state.reciterState().currentReciterName(),
            showSheet = true,
            isPlaying = audioState.isAudioPlaying(),
            onPlayClicked = { surahPlayerViewModel.onPlayWholeClicked() },
            onNextAyahClicked = { surahPlayerViewModel.onNextAyahClicked() },
            onPreviousAyahClicked = { surahPlayerViewModel.onPreviousAyahClicked() },
            onNextSurahClicked = { surahPlayerViewModel.onNextSurahClicked() },
            onPreviousSurahClicked = { surahPlayerViewModel.onPreviousSurahClicked() },
            onSeekRequested = { newPosMs -> surahPlayerViewModel.seekTo(newPosMs) },
            onSurahAndAyahClicked = { /* по желанию */ },
            onReciterClicked = { surahDetailViewModel.showReciterDialog(true) },
            onDismiss = { surahDetailViewModel.showPlayerBottomSheet(false) })
    }
}