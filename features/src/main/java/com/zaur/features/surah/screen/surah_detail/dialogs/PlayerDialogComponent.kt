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
            val surahNumber = if (uiData.surahDetailState().audioPlayerState().currentSurahNumber() != 0)
                uiData.surahDetailState().audioPlayerState().currentSurahNumber()
            else uiData.surahDetailState().textState().currentSurahNumber()
            PlayerDialog(
                soundDuration = uiData.surahDetailState().audioPlayerState().duration(),
                soundPosition = uiData.surahDetailState().audioPlayerState().position(),
                colors = colors,
                surahName = uiData.surahDetailState().textState().surahName(),
                ayahNumber = uiData.surahDetailState().audioPlayerState().currentAyah(),
                surahNumber = surahNumber,
                reciterName = uiData.surahDetailState().reciterState().currentReciterName(),
                showSheet = uiData.surahDetailState().bottomSheetState().showPlayerBottomSheet(),
                isPlaying = uiData.surahDetailState().audioPlayerState().isAudioPlaying(),
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
                onDismiss = { surahDetailViewModel().showPlayerBottomSheet(false) })
        }
    }
}