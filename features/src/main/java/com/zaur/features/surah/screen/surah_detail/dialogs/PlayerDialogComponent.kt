package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import com.zaur.features.surah.screen.surah_detail.SurahDetailDependencies
import com.zaur.features.surah.screen.surah_detail.SurahDetailUiData
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 22.05.2025
 */

interface SurahAndAyahClickListener {
    fun onClick(surahNumber: Int, ayahNumber: Int)
}

@Composable
fun PlayerDialogComponent(
    colors: QuranColors,
    uiData: SurahDetailUiData,
    deps: SurahDetailDependencies,
) {
    with(uiData) {
        with(deps) {
            with(surahDetailState()) {
                val surahNumber = audioPlayerState().currentSurahNumber()
                PlayerDialog(
                    soundDuration = audioPlayerState().duration(),
                    soundPosition = audioPlayerState().position(),
                    colors = colors,
                    surahName = audioPlayerState().surahName(),
                    ayahNumber = audioPlayerState().currentAyah(),
                    surahNumber = surahNumber,
                    reciterName = reciterState().currentReciterName(),
                    showSheet = bottomSheetState().showPlayerBottomSheet(),
                    isPlaying = audioPlayerState().isAudioPlaying(),
                    onPlayClicked = {
                        quranAudioViewModel().onPlayWholeClicked()
                    },
                    onNextAyahClicked = {
                        quranAudioViewModel().onNextAyahClicked()
                    },
                    onPreviousAyahClicked = {
                        quranAudioViewModel().onPreviousAyahClicked()
                    },
                    onNextSurahClicked = {
                        quranAudioViewModel().onNextSurahClicked()
                    },
                    onPreviousSurahClicked = {
                        quranAudioViewModel().onPreviousSurahClicked()
                    },
                    onSeekRequested = { newPosMs ->
                        // здесь вызываем метод плеера, который вы в себе реализуете, например:
                        quranAudioViewModel().seekTo(newPosMs)
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