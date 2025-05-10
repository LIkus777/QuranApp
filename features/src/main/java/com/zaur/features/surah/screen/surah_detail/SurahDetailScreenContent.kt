package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

@Composable
fun SurahDetailScreenContent(
    surahName: String,
    chapterNumber: Int,
    themeViewModel: ThemeViewModel,
    offlineViewModel: OfflineViewModel,
    surahDetailViewModel: SurahDetailViewModel,
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    controller: NavHostController,
    onMenuClick: () -> Unit,
) {
    val offlineState by offlineViewModel.offlineState().collectAsState()
    val textState by quranTextViewModel.textState().collectAsState()
    val audioState by quranAudioViewModel.audioState().collectAsState()
    val translateState by quranTranslationViewModel.translationState().collectAsState()
    val surahDetailState by surahDetailViewModel.getState().collectAsState()
    surahDetailViewModel.setSurahNumber(chapterNumber)
    surahDetailViewModel.setOfflineMode(offlineState.isOffline())
    surahDetailViewModel.fontSizeArabic(quranTextViewModel.getFontSizeArabic())
    surahDetailViewModel.fontSizeRussian(quranTextViewModel.getFontSizeRussian())
    val isDarkTheme = themeViewModel.getIsDarkTheme()

    val listState = rememberLazyListState()
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    SurahDetailEffects(
        chapterNumber = chapterNumber,
        textState = textState,
        audioState = audioState,
        surahDetailState = surahDetailState,
        listState = listState,
        quranAudioViewModel = quranAudioViewModel,
        quranTextViewModel = quranTextViewModel,
        quranTranslationViewModel = quranTranslationViewModel,
        surahDetailViewModel = surahDetailViewModel
    )

    SreenContent(
        isDarkTheme,
        chapterNumber,
        textState,
        translateState,
        surahDetailState,
        listState,
        surahDetailViewModel,
        quranAudioViewModel,
        colors,
        surahName,
        onMenuClick
    )

    SettingsBottomSheetComponent(surahDetailState, colors, surahDetailViewModel)

    ChooseTextDialogComponent(
        colors,
        surahDetailState,
        isDarkTheme,
        quranTextViewModel,
        themeViewModel,
        surahDetailViewModel
    )

    ChooseReciterDialogComponent(
        surahDetailState, colors, surahDetailViewModel, quranAudioViewModel
    )
}