package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranPageViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.features.surah.viewmodel.ScreenContentViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

/**
 * @author Zaur
 * @since 2025-05-12
 */

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
    screenContentViewModel: ScreenContentViewModel,
    quranPageViewModel: QuranPageViewModel,
    controller: NavHostController,
    onMenuClick: () -> Unit,
) {
    val offlineState by offlineViewModel.offlineState().collectAsState()
    val textState by quranTextViewModel.textState().collectAsState()
    val audioState by quranAudioViewModel.audioState().collectAsState()
    val translateState by quranTranslationViewModel.translationState().collectAsState()
    val surahDetailState by surahDetailViewModel.surahDetailState().collectAsState()
    val pageState by quranPageViewModel.pageState().collectAsState()
    surahDetailViewModel.setSurahNumber(chapterNumber)
    surahDetailViewModel.setOfflineMode(offlineState.isOffline())
    surahDetailViewModel.fontSizeArabic(quranTextViewModel.getFontSizeArabic())
    surahDetailViewModel.fontSizeRussian(quranTextViewModel.getFontSizeRussian())
    val isDarkTheme = themeViewModel.getIsDarkTheme()
    val isSurahMode = surahDetailState.uiPreferencesState().showSurahMode()

    val listState = rememberLazyListState()
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    SurahDetailEffects(
        pageNumber =  quranTextViewModel.getLastReadPosition().second,
        chapterNumber = chapterNumber,
        audioState = audioState,
        surahDetailState = surahDetailState,
        quranAudioViewModel = quranAudioViewModel,
        quranTextViewModel = quranTextViewModel,
        quranTranslationViewModel = quranTranslationViewModel,
        surahDetailViewModel = surahDetailViewModel,
        quranPageViewModel = quranPageViewModel
    )

    SreenContent(
        isDarkTheme,
        chapterNumber,
        pageState,
        textState,
        translateState,
        surahDetailState,
        listState,
        quranTextViewModel,
        screenContentViewModel,
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
        isSurahMode,
        screenContentViewModel,
        quranTextViewModel,
        themeViewModel,
        surahDetailViewModel
    )

    ChooseReciterDialogComponent(
        surahDetailState, colors, surahDetailViewModel, quranAudioViewModel
    )
}