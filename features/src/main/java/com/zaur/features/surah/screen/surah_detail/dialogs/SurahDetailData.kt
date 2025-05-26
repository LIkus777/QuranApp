package com.zaur.features.surah.screen.surah_detail.dialogs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.zaur.features.surah.screen.surah_detail.SurahDetailDependencies
import com.zaur.features.surah.screen.surah_detail.SurahDetailUiData
import com.zaur.presentation.ui.ui_state.offline.OfflineUIState

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
    Log.i("bug", "rememberSurahDetailUiData: CALLED")
    with(deps) {
        val offlineState by offlineViewModel().offlineState().collectAsState()
        val textState by quranTextViewModel().textState().collectAsState()
        val audioState by quranAudioViewModel().audioState().collectAsState()
        val translateState by quranTranslationViewModel().translationState().collectAsState()
        val surahDetailState by surahDetailViewModel().surahDetailState().collectAsState()
        val pageState by quranPageViewModel().pageState().collectAsState()
        val isDarkTheme = themeViewModel().themeState().collectAsState().value.isDarkTheme
        val isSurahMode = surahDetailState.uiPreferencesState().showSurahMode()

        val alreadyInitialized = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            if (!alreadyInitialized.value) {
                alreadyInitialized.value = true
                Log.i("bug", "rememberSurahDetailUiData: LaunchedEffect CALLED")
                updateSurahDetailData(
                    reciter = surahDetailState.reciterState().currentReciter(),
                    ayahNumber = surahDetailState.audioPlayerState().currentAyah(),
                    surahName = surahName,
                    chapterNumber = chapterNumber,
                    deps = deps,
                    offlineState = offlineState
                )
            }
        }

        return SurahDetailUiData.Base(
            offlineState,
            textState,
            audioState,
            translateState,
            surahDetailState,
            pageState,
            isDarkTheme,
            isSurahMode,
        )
    }
}

fun updateSurahDetailData(
    reciter: String,
    ayahNumber: Int,
    surahName: String,
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    offlineState: OfflineUIState,
) = with(deps) {
    val audioVm = quranAudioViewModel()
    val textVm = quranTextViewModel()
    val detailVm = surahDetailViewModel()

    val savedAyahNumber = audioVm.getLastPlayedAyah().takeIf { it != 0 } ?: ayahNumber
    val savedSurahNumber = audioVm.getLastPlayedSurah().takeIf { it != 0 } ?: chapterNumber
    val savedSurahName = audioVm.getAudioSurahName().takeIf { it.isNotEmpty() } ?: surahName

    Log.i("res", "updateSurahDetailData: savedAyahNumber ${savedAyahNumber}")
    Log.i("res", "updateSurahDetailData: savedSurahNumber ${savedSurahNumber}")
    Log.i("res", "updateSurahDetailData: savedSurahName ${savedSurahName}")

    detailVm.apply {
        setTextSurahName(surahName)
        setTextSurahNumber(chapterNumber)

        // Устанавливаем аудио-состояние на сохранённую суру
        setAudioSurahAyah(savedAyahNumber)
        setAudioSurahName(savedSurahName)
        setAudioSurahNumber(savedSurahNumber)

        // Загружаем аятов для savedSurahNumber
        // Этот suspend-функция должна запустить SurahPlayer.setAyahs(...)
        // Через ViewModel, но здесь для примера прямой вызов
        quranAudioViewModel().getChaptersAudioOfReciter(savedSurahNumber, reciter)

        setAyahInText(textVm.getLastReadAyahPosition(chapterNumber))
        setOfflineMode(offlineState.isOffline())
        fontSizeArabic(textVm.getFontSizeArabic())
        fontSizeRussian(textVm.getFontSizeRussian())
    }

    Log.i("AUDIO_NAME", "using saved sura: $savedSurahName (#$savedSurahNumber)")
}