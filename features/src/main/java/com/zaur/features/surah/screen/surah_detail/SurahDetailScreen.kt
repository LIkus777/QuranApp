package com.zaur.features.surah.screen.surah_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.fakes.FakeQAudioRAqc
import com.zaur.features.surah.fakes.FakeQTextRAqc
import com.zaur.features.surah.fakes.FakeQTranslationRAqc
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.fakes.FakeReciterStorage
import com.zaur.features.surah.fakes.FakeThemeStorage
import com.zaur.features.surah.screen.AyaColumn
import com.zaur.features.surah.screen.ChapterBottomBar
import com.zaur.features.surah.screen.ChooseReciterDialog
import com.zaur.features.surah.screen.ChooseTextDialog
import com.zaur.features.surah.screen.SettingsBottomSheet
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranAppTheme
import com.zaur.presentation.ui.QuranColors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SurahDetailScreen(
    chapterNumber: Int,
    surahDetailViewModel: SurahDetailViewModel,
    themeViewModel: ThemeViewModel,
    quranTextViewModelFactory: QuranTextViewModelFactory,
    quranTranslationViewModelFactory: QuranTranslationViewModelFactory,
    quranAudioViewModelFactory: QuranAudioViewModelFactory,
    controller: NavHostController
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

    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val isLoading = textState.currentArabicText == null || translateState.translations == null

    LaunchedEffect(textState.currentArabicText) {
        textState.currentArabicText?.let { quranAudioViewModel.setAyahs(it) }
    }

    LaunchedEffect(audioState.verseAudioFile, surahDetailState.audioPlayerState.restartAudio) {
        if (audioState.verseAudioFile != null || surahDetailState.audioPlayerState.restartAudio) {
            quranAudioViewModel.onPlayVerse(verse = audioState.verseAudioFile!!)
            scope.launch(Dispatchers.Main) {
                if (surahDetailState.audioPlayerState.currentAyah > 0) {
                    listState.scrollToItem(surahDetailState.audioPlayerState.currentAyah - 1)
                }
            }
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

    Scaffold(
        bottomBar = {
            ChapterBottomBarComponent(
                colors = colors,
                isPlaying = surahDetailState.audioPlayerState.isAudioPlaying,
                onClickSettings = { surahDetailViewModel.showSettingsBottomSheet(true) },
                onClickReciter = { surahDetailViewModel.showTextBottomSheet(true) },
                onClickPlay = { quranAudioViewModel.onPlayWholeClicked() })
        }) { paddingValues ->
        AyaColumn(
            chapterNumber,
            surahDetailState.audioPlayerState.currentAyah,
            isLoading,
            textState,
            translateState,
            colors,
            surahDetailState.uiPreferences.fontSizeArabic,
            surahDetailState.uiPreferences.fontSizeRussian,
            surahDetailState.audioPlayerState.isAudioPlaying,
            surahDetailState.audioPlayerState.isScrollToAyah,
            listState,
            paddingValues,
        ) { ayahNumber ->
            quranAudioViewModel.onPlaySingleClicked(ayahNumber, chapterNumber)
        }
    }

    SettingsBottomSheetComponent(surahDetailState, colors, surahDetailViewModel)
    ChooseTextDialogComponent(surahDetailState, isDarkTheme, surahDetailViewModel)
    ChooseReciterDialogComponent(
        surahDetailState, colors, surahDetailViewModel, quranAudioViewModel
    )

    DisposableEffect(Unit) {
        onDispose { quranAudioViewModel.clear() }
    }
}

@Composable
fun ChapterBottomBarComponent(
    colors: QuranColors,
    isPlaying: Boolean,
    onClickSettings: () -> Unit,
    onClickReciter: (Boolean) -> Unit,
    onClickPlay: () -> Unit
) {
    ChapterBottomBar(
        colors = colors,
        isPlaying = isPlaying,
        showReciterDialog = onClickReciter,
        showSettings = onClickSettings,
        onClickPlayer = onClickPlay
    )
}

@Composable
fun SettingsBottomSheetComponent(
    state: SurahDetailScreenState, colors: QuranColors, viewModel: SurahDetailViewModel
) {
    if (state.bottomSheetState.showSettingsBottomSheet) {
        SettingsBottomSheet(
            showSheet = true,
            colors = colors,
            selectedReciter = state.reciterState.currentReciter,
            showReciterDialog = { viewModel.showReciterDialog(it) },
            showArabic = state.uiPreferences.showArabic,
            onShowArabicChange = { viewModel.showArabic(it) },
            showRussian = state.uiPreferences.showRussian,
            onShowRussianChange = { viewModel.showRussian(it) },
            onDismiss = { viewModel.showSettingsBottomSheet(false) })
    }
}

@Composable
fun ChooseTextDialogComponent(
    state: SurahDetailScreenState, isDarkTheme: Boolean, viewModel: SurahDetailViewModel
) {
    ChooseTextDialog(
        showTextDialog = state.bottomSheetState.showTextBottomSheet,
        isDarkTheme = isDarkTheme,
        fontSizeArabic = state.uiPreferences.fontSizeArabic,
        onFontSizeArabicChange = { viewModel.fontSizeArabic(it) },
        fontSizeRussian = state.uiPreferences.fontSizeRussian,
        onFontSizeRussianChange = { viewModel.fontSizeRussian(it) },
        onThemeChange = {},
        onDismiss = { viewModel.showTextBottomSheet(false) })
}

@Composable
fun ChooseReciterDialogComponent(
    state: SurahDetailScreenState,
    colors: QuranColors,
    viewModel: SurahDetailViewModel,
    audioViewModel: QuranAudioViewModel
) {
    ChooseReciterDialog(
        showDialog = state.reciterState.showReciterDialog,
        isFirstSelection = state.reciterState.isFirstSelection,
        colors = colors
    ) { identifier ->
        viewModel.showReciterDialog(false)
        audioViewModel.saveReciter(identifier)
        viewModel.selectedReciter(audioViewModel.getReciterName().toString())
    }
}


@Preview(showBackground = true)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SurahDetailScreenPreview() {
    val fakeNavController = rememberNavController()
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahDetailScreen(
                1, surahDetailViewModel = SurahDetailViewModel.Base(
                    SurahDetailStateManager.Base(SurahDetailScreenState())
                ), themeViewModel = ThemeViewModel.Base(
                    SavedStateHandle(), ThemeUseCase(FakeThemeStorage())
                ), quranTextViewModelFactory = QuranTextViewModelFactory.Base(
                    quranTextUseCaseAqc = QuranTextUseCaseAqc.Base(
                        FakeQTextRAqc(), FakeQuranStorage()
                    )
                ), quranTranslationViewModelFactory = QuranTranslationViewModelFactory.Base(
                    quranTranslationUseCaseAqc = QuranTranslationUseCaseAqc.Base(
                        FakeQTranslationRAqc()
                    )
                ), quranAudioViewModelFactory = QuranAudioViewModelFactory.Base(
                    stateManager = SurahDetailStateManager.Base(SurahDetailScreenState()),
                    quranAudioUseCaseAqc = QuranAudioUseCaseAqc.Base(
                        FakeQAudioRAqc(), FakeReciterStorage()
                    )
                ), fakeNavController
            )
        }
    }
}