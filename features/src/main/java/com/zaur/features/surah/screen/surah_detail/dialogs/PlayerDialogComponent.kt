package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import com.zaur.features.surah.screen.surah_detail.SurahDetailDependencies
import com.zaur.features.surah.screen.surah_detail.SurahDetailUiData
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 22.05.2025
 */

@Composable
fun PlayerDialogComponent(
    colors: QuranColors,
    uiData: SurahDetailUiData,
    deps: SurahDetailDependencies,
) {
    with(uiData) {
        with(deps) {
            with(surahPlayerState()) {
                val surahNumber = currentSurahNumber()
                PlayerDialog(
                    soundDuration = duration(),
                    soundPosition = position(),
                    colors = colors,
                    surahName = surahName(),
                    ayahNumber = currentAyah(),
                    surahNumber = surahNumber,
                    reciterName = uiData.surahDetailState().reciterState().currentReciterName(),
                    showSheet = uiData.surahDetailState().bottomSheetState()
                        .showPlayerBottomSheet(),
                    isPlaying = isAudioPlaying(),
                    onPlayClicked = {
                        surahPlayerViewModel().onPlayWholeClicked()
                    },
                    onNextAyahClicked = {
                        surahPlayerViewModel().onNextAyahClicked()
                    },
                    onPreviousAyahClicked = {
                        surahPlayerViewModel().onPreviousAyahClicked()
                    },
                    onNextSurahClicked = {
                        surahPlayerViewModel().onNextSurahClicked()
                    },
                    onPreviousSurahClicked = {
                        surahPlayerViewModel().onPreviousSurahClicked()
                    },
                    onSeekRequested = { newPosMs ->
                        surahPlayerViewModel().seekTo(newPosMs)
                    },
                    onSurahAndAyahClicked = {

                    },
                    onReciterClicked = {
                        deps.surahDetailViewModel().showReciterDialog(true)
                    },
                    onDismiss = { surahDetailViewModel().showPlayerBottomSheet(false) })
            }
        }
    }
}