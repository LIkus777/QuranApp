package com.zaur.features.surah.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranAppTheme
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
    LocalContext.current

    val quranTextViewModel = remember { quranTextViewModelFactory.create() }
    val quranAudioViewModel = remember { quranAudioViewModelFactory.create() }
    val quranTranslationViewModel = remember { quranTranslationViewModelFactory.create() }

    val surahDetailState by surahDetailViewModel.surahDetailUiState().collectAsState()
    val textState by quranTextViewModel.textState()
    val audioState by quranAudioViewModel.audioState()
    val translateState by quranTranslationViewModel.translationState()

    val isLoading = textState.currentArabicText == null || translateState.translations == null
    val isDarkTheme = themeViewModel.getIsDarkTheme().collectAsState(initial = false).value
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    val scope = rememberCoroutineScope()

    // Создаем LazyListState для управления прокруткой
    val listState = rememberLazyListState()

    val playerScreen = PlayerScreen.Base(surahDetailState)

    LaunchedEffect(textState.currentArabicText) {
        if (textState.currentArabicText != null) {
            val verses = textState.currentArabicText!!
            quranAudioViewModel.setAyahs(verses)
        }
    }

    // Показываем progress bar, когда загружается новый аудиофайл
    /*LaunchedEffect(audioState.verseAudioFile, surahDetailState.audioPlayerState.restartAudio) {
        if (audioState.verseAudioFile != null) {
            Log.i("TAGG", "SurahDetailScreen: IF isAudioPlaying = TRUE")
            surahDetailViewModel.updateState {
                copy(
                    audioPlayerState = audioPlayerState.copy(
                        isAudioPlaying = true,
                        restartAudio = !audioPlayerState.restartAudio
                    )
                )
            }
        }
    }*/

    /*LaunchedEffect(surahDetailState.audioPlayerState.restartAudio) {
        Log.i("TAGG", "SurahDetailScreen: IF restartAudio = TRUE")
        surahDetailViewModel.updateState {
            surahDetailState.copy(
                audioPlayerState.copy(
                    restartAudio = !surahDetailState.audioPlayerState.restartAudio
                )
            )
        }
    }*/

    // Загружаем чтеца при первом рендере экрана
    LaunchedEffect(Unit) {
        val reciter = quranAudioViewModel.getReciterName()
        surahDetailViewModel.selectedReciter(reciter.toString())
        if (reciter.isNullOrEmpty()) {
            surahDetailViewModel.showReciterDialog(true) // Показываем диалог, если чтец не выбран
        }
        quranTextViewModel.getArabicChapter(chapterNumber)
        quranTranslationViewModel.getTranslationForChapter(chapterNumber, "ru.kuliev")
        quranAudioViewModel.getChaptersAudioOfReciter(
            chapterNumber, quranAudioViewModel.getReciter().toString()
        )
    }

    playerScreen.Screen(onPlayWholeClicked = {
        quranAudioViewModel.onPlayWholeClicked()
    }, clear = {
        quranAudioViewModel.clear()
    }, scrollToAudioElement = {
        scope.launch(Dispatchers.Main) {
            // Прокрутка к нужному элементу
            if (surahDetailState.audioPlayerState.currentAyah > 0) {
                listState.animateScrollToItem(surahDetailState.audioPlayerState.currentAyah - 1)
            }
        }
    })

    Scaffold(bottomBar = {
        ChapterBottomBar(
            colors,
            isPlaying = surahDetailState.audioPlayerState.isAudioPlaying,
            showReciterDialog = { show ->
                surahDetailViewModel.showTextBottomSheet(show)
            },
            showSettings = {
                surahDetailViewModel.showSettingsBottomSheet(true)
            },
            onClickPlayer = {
                quranAudioViewModel.onPlayWholeClicked()
            })
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

    // Остальная логика для BottomSheet и выбора шрифта/рецитатора
    if (surahDetailState.bottomSheetState.showSettingsBottomSheet) {
        SettingsBottomSheet(
            showSheet = surahDetailState.bottomSheetState.showSettingsBottomSheet,
            colors = colors,
            selectedReciter = surahDetailState.reciterState.currentReciter,
            showReciterDialog = { showReciterDialog ->
                surahDetailViewModel.showReciterDialog(showReciterDialog)
            },
            showArabic = surahDetailState.uiPreferences.showArabic,
            onShowArabicChange = { showArabic ->
                surahDetailViewModel.showArabic(showArabic)
            },
            showRussian = surahDetailState.uiPreferences.showRussian,
            onShowRussianChange = { showRussian ->
                surahDetailViewModel.showRussian(showRussian)
            },
            onDismiss = {
                surahDetailViewModel.showSettingsBottomSheet(false)
            })
    }

    ChooseTextDialog(
        showTextDialog = surahDetailState.bottomSheetState.showTextBottomSheet,
        isDarkTheme = isDarkTheme,
        fontSizeArabic = surahDetailState.uiPreferences.fontSizeArabic,
        onFontSizeArabicChange = { fontSizeArabic ->
            surahDetailViewModel.fontSizeArabic(fontSizeArabic)
        },
        fontSizeRussian = surahDetailState.uiPreferences.fontSizeRussian,
        onFontSizeRussianChange = { fontSizeRussian ->
            surahDetailViewModel.fontSizeRussian(fontSizeRussian)
        },
        onThemeChange = {

        },
    ) {
        surahDetailViewModel.showTextBottomSheet(false)
    }

    ChooseReciterDialog(
        surahDetailState.reciterState.showReciterDialog,
        surahDetailState.reciterState.isFirstSelection,
        colors
    ) { identifier ->
        surahDetailViewModel.showReciterDialog(false)
        quranAudioViewModel.saveReciter(identifier)
        surahDetailViewModel.showReciterDialog(false)
        surahDetailViewModel.selectedReciter(quranAudioViewModel.getReciterName().toString())
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
                    SurahDetailStateManager.Base()
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
                    stateManager = SurahDetailStateManager.Base(),
                    quranAudioUseCaseAqc = QuranAudioUseCaseAqc.Base(
                        FakeQAudioRAqc(), FakeReciterStorage()
                    )
                ), fakeNavController
            )
        }
    }
}