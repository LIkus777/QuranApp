package com.zaur.features.surah.screen.surah_detail.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.zaur.features.surah.screen.surah_detail.SurahDetailDependencies
import com.zaur.features.surah.screen.surah_detail.SurahDetailUiData

/**
 * @author Zaur
 * @since 25.05.2025
 */

@Composable
fun rememberSurahDetailUiData(
    surahName: String,
    chapterNumber: Int,
    deps: SurahDetailDependencies,
): SurahDetailUiData {
    // 1) Считываем всё состояние из ViewModel-ов одним блоком
    val offlineState by deps.offlineViewModel().offlineState().collectAsState()
    val textState by deps.quranTextViewModel().textState().collectAsState()
    val audioState by deps.surahPlayerViewModel().audioState().collectAsState()
    val translateState by deps.quranTranslationViewModel().translationState().collectAsState()
    val detailState by deps.surahDetailViewModel().surahDetailState().collectAsState()
    val pageState by deps.quranPageViewModel().pageState().collectAsState()
    val isDarkTheme by deps.themeViewModel().themeState().collectAsState()
    val isSurahMode = detailState.uiPreferencesState().showSurahMode()

    val playerVm = deps.surahPlayerViewModel()
    val reciterId = playerVm.getReciter().orEmpty()

    // 2) Перезапускаем каждый раз, когда chapterNumber или reciterId меняются
    LaunchedEffect(chapterNumber, reciterId) {
        // Сохраняем в ViewModel выбранного reciter
        deps.surahDetailViewModel().selectedReciter(reciterId, playerVm.getReciterName().orEmpty())

        // Каждый раз грузим новый аудио-набор
        playerVm.getChaptersAudioOfReciter(chapterNumber, reciterId)

        // Инициализируем остальные поля в detailVm
        val textVm = deps.quranTextViewModel()
        val detailVm = deps.surahDetailViewModel()

        val savedAyah =
            playerVm.getLastPlayedAyah().takeIf { it != 0 } ?: detailState.audioPlayerState()
                .currentAyah()
        val savedSurah = playerVm.getLastPlayedSurah().takeIf { it != 0 } ?: chapterNumber
        val savedName = playerVm.getAudioSurahName().takeIf(String::isNotEmpty) ?: surahName

        detailVm.apply {
            setTextSurahName(surahName)
            setTextSurahNumber(chapterNumber)
            setAudioSurahAyah(savedAyah)
            setAudioSurahNumber(savedSurah)
            setAudioSurahName(savedName)
            setAyahInText(textVm.getLastReadAyahPosition(chapterNumber))
            setOfflineMode(offlineState.isOffline())
            fontSizeArabic(textVm.getFontSizeArabic())
            fontSizeRussian(textVm.getFontSizeRussian())
        }
    }

    return SurahDetailUiData.Base(
        offlineState,
        textState,
        audioState,
        translateState,
        detailState,
        pageState,
        isDarkTheme.isDarkTheme,
        isSurahMode,
    )
}