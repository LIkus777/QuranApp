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
            PlayerDialog(
                colors = colors,
                surahName = uiData.surahDetailState().textState().surahName(),
                ayahNumber = uiData.surahDetailState().audioPlayerState().currentAyah(),
                surahNumber = uiData.surahDetailState().audioPlayerState().currentSurahNumber(),
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
                onDismiss = { surahDetailViewModel().showPlayerBottomSheet(false) })
        }
    }
}