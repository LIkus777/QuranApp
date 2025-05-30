package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    contentPadding: PaddingValues = PaddingValues(),
) {
    // 1) Подписка на состояние
    val detailState = surahDetailViewModel.surahDetailState().collectAsState()
    val audioState = detailState.value.audioPlayerState()

    // 2) При первом появлении диалога — восстанавливаем последний трек
    LaunchedEffect(detailState.value.bottomSheetState().showPlayerBottomSheet()) {
        if (detailState.value.bottomSheetState().showPlayerBottomSheet()) {
            val lastAyah = surahPlayerViewModel.getLastPlayedAyah().takeIf { it != 0 } ?: 1
            val lastSurah = surahPlayerViewModel.getLastPlayedSurah().takeIf { it != 0 } ?: 1
            val lastName =
                surahPlayerViewModel.getAudioSurahName().takeIf(String::isNotEmpty) ?: "Al-Fatiha"

            // Устанавливаем в DetailViewModel
            surahDetailViewModel.apply {
                setTextSurahName(lastName)
                setTextSurahNumber(lastSurah)
                setAudioSurahName(lastName)
                setAudioSurahNumber(lastSurah)
                setAudioSurahAyah(lastAyah)
                selectedReciter(
                    surahPlayerViewModel.getReciter().orEmpty(),
                    surahPlayerViewModel.getReciterName().orEmpty()
                )
            }

            // Загружаем аудио из SurahPlayerViewModel
            surahPlayerViewModel.getChaptersAudioOfReciter(
                lastSurah, surahPlayerViewModel.getReciter().orEmpty()
            )
        }
    }

    // 3) Если надо показывать диалог — рисуем PlayerDialog
    if (detailState.value.bottomSheetState().showPlayerBottomSheet()) {
        PlayerDialog(
            contentPadding = contentPadding,
            soundDuration = audioState.duration(),
            soundPosition = audioState.position(),
            colors = colors,
            surahName = audioState.surahName(),
            ayahNumber = audioState.currentAyah(),
            surahNumber = audioState.currentSurahNumber(),
            reciterName = detailState.value.reciterState().currentReciterName(),
            showSheet = true,
            isPlaying = audioState.isAudioPlaying(),
            onPlayClicked = { surahPlayerViewModel.onPlayWholeClicked() },
            onNextAyahClicked = { surahPlayerViewModel.onNextAyahClicked() },
            onPreviousAyahClicked = { surahPlayerViewModel.onPreviousAyahClicked() },
            onNextSurahClicked = { surahPlayerViewModel.onNextSurahClicked() },
            onPreviousSurahClicked = { surahPlayerViewModel.onPreviousSurahClicked() },
            onSeekRequested = { surahPlayerViewModel.seekTo(it) },
            onReciterClicked = { surahDetailViewModel.showReciterDialog(true) },
            onDismiss = { surahDetailViewModel.showPlayerBottomSheet(false) })
    }
}