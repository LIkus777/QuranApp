package com.zaur.features.surah.screen.surah_detail

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors

@Composable
fun SurahDetailScreenContent(
    surahName: String,
    chapterNumber: Int,
    themeViewModel: ThemeViewModel,
    surahDetailViewModel: SurahDetailViewModel,
    quranTextViewModelFactory: QuranTextViewModelFactory,
    quranAudioViewModelFactory: QuranAudioViewModelFactory,
    quranTranslationViewModelFactory: QuranTranslationViewModelFactory,
    controller: NavHostController,
    onMenuClick: () -> Unit,
) {
    val quranTextViewModel = remember { quranTextViewModelFactory.create() }
    val quranAudioViewModel = remember { quranAudioViewModelFactory.create() }
    val quranTranslationViewModel = remember { quranTranslationViewModelFactory.create() }

    val textState by quranTextViewModel.textState().collectAsState()
    val audioState by quranAudioViewModel.audioState().collectAsState()
    val translateState by quranTranslationViewModel.translationState().collectAsState()
    val surahDetailState by surahDetailViewModel.getState().collectAsState()
    surahDetailViewModel.setSurahNumber(chapterNumber)
    val isDarkTheme = themeViewModel.getIsDarkTheme().collectAsState(initial = false).value

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
    ChooseTextDialogComponent(colors, surahDetailState, isDarkTheme, themeViewModel, surahDetailViewModel)
    ChooseReciterDialogComponent(
        surahDetailState,
        colors,
        surahDetailViewModel,
        quranAudioViewModel
    )
}