package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.zaur.domain.al_quran_cloud.models.arabic.ArabicChapter
import com.zaur.domain.al_quran_cloud.models.translate.TranslationAqc
import com.zaur.features.surah.ui_state.AnimatedMenuUiState
import com.zaur.features.surah.ui_state.SurahDetailUiState
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.ScreenContentViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SreenContent(
    isDarkTheme: Boolean,
    chapterNumber: Int,
    textState: QuranTextAqcUIState,
    translateState: QuranTranslationAqcUIState,
    surahDetailState: SurahDetailScreenState,
    listState: LazyListState,
    screenContentViewModel: ScreenContentViewModel,
    surahDetailViewModel: SurahDetailViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    colors: QuranColors,
    surahName: String,
    onMenuClick: () -> Unit,
) {
    val isLoading =
        textState.currentArabicText() == ArabicChapter.Empty || translateState.translations() == TranslationAqc.Empty

    val isBarsVisible = remember { mutableStateOf(true) }

    val surahMode = screenContentViewModel.surahMode().collectAsState()
    val animatedMenu = screenContentViewModel.animatedMenu().collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }) {
                isBarsVisible.value = !isBarsVisible.value
            }) {

        // Основной контент на фоне
        when (surahMode.value) {
            is SurahDetailUiState.SurahModeState -> surahMode.value.Render(
                state = screenContentViewModel.fetchAyaListItem(isLoading, chapterNumber), //todo
                colors = colors,
                isDarkTheme = isDarkTheme,
                chapterNumber = chapterNumber,
                soundIsActive = surahDetailState.audioPlayerState().isAudioPlaying(),
                ayaNumber = surahDetailState.audioPlayerState().currentAyahInSurah(),
                fontSizeArabic = surahDetailState.uiPreferencesState().fontSizeArabic(),
                fontSizeRussian = surahDetailState.uiPreferencesState().fontSizeRussian(),
                showArabic = surahDetailState.uiPreferencesState().showArabic(),
                showRussian = surahDetailState.uiPreferencesState().showRussian(),
                translations = translateState.translations().translationAyahs(),
                ayats = textState.currentArabicText().ayahs(),
                listState = listState,
                onClickSound = { ayahNumber, ayahNumberInSurah ->
                    surahDetailViewModel.setAyahInSurahNumber(ayahNumberInSurah)
                    Log.i(
                        "TAG",
                        "SreenContent: ayahNumber $ayahNumber ayahNumberInSurah $ayahNumberInSurah"
                    )
                    quranAudioViewModel.onPlaySingleClicked(ayahNumberInSurah, chapterNumber)
                })

            is SurahDetailUiState.PageModeState -> surahMode.value.Render()
        }

        // TopBar поверх контента
        when (animatedMenu.value) {
            is AnimatedMenuUiState.Animate -> animatedMenu.value.Render(
                isPlaying = surahDetailState.audioPlayerState().isAudioPlaying(),
                surahName = surahName,
                isBarsVisible = isBarsVisible.value,
                colors = colors,
                onMenuClick = onMenuClick,
                onClickSettings = { surahDetailViewModel.showSettingsBottomSheet(true) },
                onClickReciter = { surahDetailViewModel.showTextBottomSheet(true) },
                onClickPlay = { quranAudioViewModel.onPlayWholeClicked() },
            )
        }
    }
}