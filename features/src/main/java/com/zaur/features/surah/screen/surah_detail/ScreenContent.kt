package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.translate.Translation
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel
import com.zaur.presentation.ui.QuranColors
import com.zaur.presentation.ui.ui_state.AnimatedMenuUiState
import com.zaur.presentation.ui.ui_state.SurahDetailUiState

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun ScreenContent(
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    uiData: SurahDetailUiData,
    colors: QuranColors,
    surahName: String,
    onMenuClick: () -> Unit,
) {
    // 1. Распакуем то, что нам нужно из uiData и deps
    val textState = uiData.textState()
    val translateState = uiData.translateState()
    val surahDetailState = uiData.surahDetailState()
    val surahPlayerState = uiData.surahPlayerState()
    val pageState = uiData.pageState()
    val isDarkTheme = uiData.isDarkTheme()

    val quranTextViewModel = deps.quranTextViewModel()
    val quranPageViewModel = deps.quranPageViewModel()
    val surahDetailViewModel = deps.surahDetailViewModel()
    val surahPlayerViewModel = deps.surahPlayerViewModel()
    val screenContentViewModel = deps.screenContentViewModel()

    // 2. Соберём flow-ы в state (collectAsState единожды)
    val surahModeState = screenContentViewModel.surahMode().collectAsState()
    val animatedMenuState = screenContentViewModel.animatedMenu().collectAsState()
    val isBarsVisible = rememberSaveable { mutableStateOf(false) }
    val isLoading = remember(textState, translateState) {
        textState.currentArabicText() == ArabicChapter.Empty || translateState.translations() == Translation.Empty
    }

    val translator = deps.translatorManager().getTranslator().toString()

    // Загрузка страниц только когда в PageMode
    LaunchedEffect(surahModeState.value, chapterNumber) {
        if (surahModeState.value is SurahDetailUiState.PageModeState) {
            val lastPage = quranPageViewModel.getLastReadPagePosition()
            quranPageViewModel.getUthmaniPage(lastPage)
            quranPageViewModel.getTranslatedPage(lastPage, translator)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }) {
                isBarsVisible.value = !isBarsVisible.value
            }) {
        when (val mode = surahModeState.value) {
            is SurahDetailUiState.SurahModeState -> {
                // fetchAyaListItem возвращает AyaListItem.Loading или AyaListItem.AyahListItem
                val ayaListItem = screenContentViewModel.fetchAyaListItem(isLoading, chapterNumber)
                mode.RenderSurahMode(
                    state = ayaListItem,
                    chapterNumber = chapterNumber,
                    surahDetailState = surahDetailState,
                    surahPlayerState = surahPlayerState,
                    ayats = textState.currentArabicText().ayahs(),
                    isDarkTheme = isDarkTheme,
                    colors = colors,
                    onAyahItemChanged = { idx ->
                        quranTextViewModel.saveLastReadAyahPosition(chapterNumber, idx)
                    },
                    onPageItemChanged = { pg ->
                        quranPageViewModel.saveLastReadPagePosition(pg)
                    },
                    onClickSound = { ay, inSurah ->
                        surahPlayerViewModel.setAudioSurahAyah(inSurah)
                        surahPlayerViewModel.onPlaySingleClicked(inSurah, chapterNumber)
                    },
                    onListenSurahClicked = {
                        startSurahPlayback(
                            surahPlayerViewModel,
                            chapterNumber,
                            surahName,
                            surahDetailState.reciterState().currentReciter()
                        )
                    },
                    translations = translateState.translations().translationAyahs()
                )
            }

            is SurahDetailUiState.PageModeState -> {
                mode.RenderPageMode(
                    pageState = pageState,
                    surahDetailState = surahDetailState,
                    surahPlayerState = surahPlayerState,
                    isDarkTheme = isDarkTheme,
                    colors = colors,
                    onClickPreviousPage = { quranTextViewModel.onPreviousPage() },
                    onClickNextPage = { quranTextViewModel.onNextPage() },
                    onClickSound = { ay, inSurah ->
                        surahPlayerViewModel.setAudioSurahAyah(inSurah)
                        surahPlayerViewModel.onPlaySingleClicked(inSurah, chapterNumber)
                    })
            }

            is SurahDetailUiState.Loading -> {
                mode.Render() // уже рисует CircularProgressIndicator()
            }
        }

        if (animatedMenuState.value is AnimatedMenuUiState.Animate) {
            (animatedMenuState.value as AnimatedMenuUiState.Animate).Render(
                surahName = surahName,
                isBarsVisible = isBarsVisible.value,
                colors = colors,
                onMenuClick = onMenuClick,
                onClickSettings = { surahDetailViewModel.showSettingsBottomSheet(true) },
                onClickReciter = { surahDetailViewModel.showTextBottomSheet(true) },
                onClickPlay = { surahDetailViewModel.showPlayerBottomSheet(true) })
        }
    }
}

// Вспомогательная функция для запуска воспроизведения всей суры
fun startSurahPlayback(
    surahPlayerViewModel: SurahPlayerViewModel,
    chapterNumber: Int,
    surahName: String,
    reciter: String,
) {
    // Останавливаем текущее
    surahPlayerViewModel.onStopClicked()
    // Сбрасываем позицию на 1-й аят
    surahPlayerViewModel.setAudioSurahAyah(1)
    surahPlayerViewModel.setLastPlayedAyah(1)
    // Сохраняем имя и номер суры
    surahPlayerViewModel.setAudioSurahName(surahName)
    surahPlayerViewModel.setAudioSurahNameSharedPref(surahName)
    surahPlayerViewModel.setAudioSurahNumber(chapterNumber)
    surahPlayerViewModel.setLastPlayedSurah(chapterNumber)
    // Запустить новую суру
    surahPlayerViewModel.playFromStartSurahAudio(chapterNumber, reciter)
}