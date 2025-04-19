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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Log.i("TAG", "SurahDetailScreenContent: surahDetailState $surahDetailState ")

    rememberCoroutineScope()
    val listState = rememberLazyListState()
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors
    val isLoading = textState.currentArabicText == null || translateState.translations == null

    val isBarsVisible = remember { mutableStateOf(true) }

    LaunchedEffect(textState.currentArabicText) {
        textState.currentArabicText?.ayahs?.let {
            quranAudioViewModel.setAyahs(it)
        }
    }

    LaunchedEffect(audioState.verseAudioFile, surahDetailState.audioPlayerState.restartAudio) {
        if (audioState.verseAudioFile != null || surahDetailState.audioPlayerState.restartAudio) {
            quranAudioViewModel.onPlayVerse(verse = audioState.verseAudioFile!!)
        }
    }

    LaunchedEffect(surahDetailState.audioPlayerState.currentAyahInSurah) {
        val index = surahDetailState.audioPlayerState.currentAyahInSurah - 1
        Log.i("TAGGG", "SurahDetailScreenContent: index $index")
        if (index >= 0) {
            listState.animateScrollToItem(index)
        }
    }

    LaunchedEffect(chapterNumber) {
        val reciter = quranAudioViewModel.getReciterName()
        surahDetailViewModel.selectedReciter(reciter.toString())
        if (reciter.isNullOrEmpty()) {
            surahDetailViewModel.showReciterDialog(true)
        }
        quranTextViewModel.getArabicChapter(chapterNumber)
        quranTranslationViewModel.getTranslationForChapter(chapterNumber, "ru.kuliev")
        quranAudioViewModel.getChaptersAudioOfReciter(
            chapterNumber, quranAudioViewModel.getReciter().toString()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }) {
                isBarsVisible.value = !isBarsVisible.value
            }) {
        // Основной контент на фоне
        AyaColumn(
            chapterNumber = chapterNumber,
            currentAyahInSurah = surahDetailState.audioPlayerState.currentAyahInSurah,
            isLoading = isLoading,
            textState = textState,
            translateState = translateState,
            colors = colors,
            fontSizeArabic = surahDetailState.uiPreferences.fontSizeArabic,
            fontSizeRussian = surahDetailState.uiPreferences.fontSizeRussian,
            soundIsActive = surahDetailState.audioPlayerState.isAudioPlaying,
            listState = listState,
            onClickSound = { ayahNumber, ayahNumberInSurah ->
                Log.i("TAGGG", "SurahDetailScreenContent: ayahNumber  $ayahNumber")
                Log.i("TAGGG", "SurahDetailScreenContent: ayahNumberInSurah $ayahNumberInSurah")
                surahDetailViewModel.setAyahInSurahNumber(ayahNumberInSurah)
                quranAudioViewModel.onPlaySingleClicked(ayahNumber, chapterNumber)
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

    SettingsBottomSheetComponent(surahDetailState, colors, surahDetailViewModel)
    ChooseTextDialogComponent(colors, surahDetailState, isDarkTheme, surahDetailViewModel)
    ChooseReciterDialogComponent(
        surahDetailState, colors, surahDetailViewModel, quranAudioViewModel
    )

    DisposableEffect(Unit) {
        onDispose { quranAudioViewModel.clear() }
    }
}