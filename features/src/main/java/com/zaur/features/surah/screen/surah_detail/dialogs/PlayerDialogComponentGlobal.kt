package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.QuranColors


/**
 * @author Zaur
 * @since 26.05.2025
 */

@Composable
fun PlayerDialogComponentGlobal(
    navController: NavHostController,
    surahDetailViewModel: SurahDetailViewModel,
    surahPlayerViewModel: SurahPlayerViewModel,
    colors: QuranColors,
    contentPadding: PaddingValues = PaddingValues(),
) {
    // 1) Подписка на состояние
    val detailState = surahDetailViewModel.surahDetailState().collectAsState()
    val playerState = surahPlayerViewModel.surahPlayerState().collectAsState()
    val audioState = playerState.value

    // 2) При первом появлении диалога — восстанавливаем последний трек
    LaunchedEffect(detailState.value.bottomSheetState().showPlayerBottomSheet()) {
        if (detailState.value.bottomSheetState().showPlayerBottomSheet()) {
            val lastAyah = surahPlayerViewModel.getLastPlayedAyah().takeIf { it != 0 } ?: 1
            val lastSurah = surahPlayerViewModel.getLastPlayedSurah().takeIf { it != 0 } ?: 1
            val lastName =
                surahPlayerViewModel.getAudioSurahNameSharedPref().takeIf(String::isNotEmpty)
                    ?: "Al-Fatiha"

            // Устанавливаем в DetailViewModel
            surahDetailViewModel.apply {
                setTextSurahName(lastName)
                setTextSurahNumber(lastSurah)
                selectedReciter(
                    surahPlayerViewModel.getReciter().orEmpty(),
                    surahPlayerViewModel.getReciterName().orEmpty()
                )
            }
            surahPlayerViewModel.apply {
                setAudioSurahName(lastName)
                setAudioSurahNumber(lastSurah)
                setAudioSurahAyah(lastAyah)
            }

            // Загружаем аудио из SurahPlayerViewModel
            surahPlayerViewModel.getSurahAudioByNumberAndReciter(
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
            onRepeatClicked = { surahDetailViewModel.showRepeatDialog(true) },
            onSpeedClicked = {  }, // todo
            onSurahAndAyahClicked = {
                // Здесь делаем навигацию на экран SurahDetail,
                // передавая текущую surahNumber и surahName
                val number = audioState.currentSurahNumber()
                val name   = audioState.surahName()
                // Например, если ваш route = "surah_detail_screen/{surahNumber}/{surahName}"
                navController.navigate(
                    Screen.SurahDetail.createRoute(number, name)
                ) {
                    // Не возвращаться к старому экрану плеера,
                    // а сразу перезаписать стек:
                    popUpTo(Screen.MainScreen.route) { inclusive = false }
                    launchSingleTop = true
                }
            },
            onDismiss = { surahDetailViewModel.showPlayerBottomSheet(false) }
        )
    }

    if (detailState.value.bottomSheetState().showRepeatDialog()) {
        RepeatDialog(
            showSheet = true, onDismiss = { surahDetailViewModel.showRepeatDialog(false) }
        )
    }
}