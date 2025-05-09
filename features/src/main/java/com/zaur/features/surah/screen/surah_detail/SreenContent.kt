package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.presentation.ui.QuranColors

@Composable
fun SreenContent(
    isDarkTheme: Boolean,
    chapterNumber: Int,
    textState: QuranTextAqcUIState,
    translateState: QuranTranslationAqcUIState,
    surahDetailState: SurahDetailScreenState,
    listState: LazyListState,
    surahDetailViewModel: SurahDetailViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    colors: QuranColors,
    surahName: String,
    onMenuClick: () -> Unit,
) {
    val isLoading = textState.currentArabicText == null || translateState.translations == null

    val isBarsVisible = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }) {
                isBarsVisible.value = !isBarsVisible.value
            }) {
        // Основной контент на фоне
        AyaColumn(
            isDarkTheme = isDarkTheme,
            chapterNumber = chapterNumber,
            currentAyahInSurah = surahDetailState.audioPlayerState.currentAyahInSurah,
            isLoading = isLoading,
            textState = textState,
            translateState = translateState,
            colors = colors,
            surahDetailState.uiPreferences.showArabic,
            surahDetailState.uiPreferences.showRussian,
            fontSizeArabic = surahDetailState.uiPreferences.fontSizeArabic,
            fontSizeRussian = surahDetailState.uiPreferences.fontSizeRussian,
            soundIsActive = surahDetailState.audioPlayerState.isAudioPlaying,
            listState = listState,
            onClickSound = { ayahNumber, ayahNumberInSurah ->
                surahDetailViewModel.setAyahInSurahNumber(ayahNumberInSurah)
                Log.i("TAG", "SreenContent: ayahNumber $ayahNumber ayahNumberInSurah $ayahNumberInSurah")
                quranAudioViewModel.onPlaySingleClicked(ayahNumberInSurah, chapterNumber)
            })

        // TopBar поверх контента
        AnimatedVisibility(
            visible = isBarsVisible.value, enter = fadeIn(), exit = fadeOut()
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
                ChapterTopBarComponent(
                    surahName = surahName,
                    onMenuClick = onMenuClick,
                    colors = colors,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }
        }

        // BottomBar поверх контента
        AnimatedVisibility(
            visible = isBarsVisible.value, enter = fadeIn(), exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
            ) {
                ChapterBottomBarComponent(
                    colors = colors,
                    isPlaying = surahDetailState.audioPlayerState.isAudioPlaying,
                    onClickSettings = { surahDetailViewModel.showSettingsBottomSheet(true) },
                    onClickReciter = { surahDetailViewModel.showTextBottomSheet(true) },
                    onClickPlay = { quranAudioViewModel.onPlayWholeClicked() },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}