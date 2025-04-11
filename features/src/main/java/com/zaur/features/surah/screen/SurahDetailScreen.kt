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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.zaur.features.surah.ui_state.aqc.SurahDetailState
import com.zaur.features.surah.ui_state.aqc.SurahDetailStateCallback
import com.zaur.features.surah.viewmodel.QuranAudioVmCallback
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
    themeViewModel: ThemeViewModel,
    quranTextViewModelFactory: QuranTextViewModelFactory,
    quranTranslationViewModelFactory: QuranTranslationViewModelFactory,
    quranAudioViewModelFactory: QuranAudioViewModelFactory,
    controller: NavHostController
) {
    val context = LocalContext.current

    val quranTextViewModel = remember { quranTextViewModelFactory.create() }
    val quranAudioViewModel = remember { quranAudioViewModelFactory.create() }
    val quranTranslationViewModel = remember { quranTranslationViewModelFactory.create() }

    val textState by quranTextViewModel.textUiState.collectAsState()
    val audioState by quranAudioViewModel.audioUiState.collectAsState()
    val translateState by quranTranslationViewModel.translationUiState.collectAsState()

    val isLoading = textState.currentArabicText == null || translateState.translations == null
    val isDarkTheme = themeViewModel.getIsDarkTheme().collectAsState(initial = false).value
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    var surahDetailStateData by remember { mutableStateOf(SurahDetailState()) }

    // Создаем LazyListState для управления прокруткой
    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    val player = Player.Base(context, surahDetailStateData, audioState)
    val playerScreen = PlayerScreen.Base(surahDetailStateData)

    player.setSurahDetailStateCallback(object : SurahDetailStateCallback {
        override fun update(state: SurahDetailState) {
            Log.i("TAGG", "update state $state}")
            surahDetailStateData = state
        }
    })
    player.setQuranAudioVmCallback(object : QuranAudioVmCallback {
        override fun callVerseAudioFile() {
            Log.i("TAGG", "callVerseAudioFile CALLED}")
            quranAudioViewModel.getVerseAudioFile(
                "$chapterNumber:${surahDetailStateData.currentAyah}",
                quranTextViewModel.getReciter().toString()
            )
        }
    })

    // Показываем progress bar, когда загружается новый аудиофайл
    LaunchedEffect(audioState.verseAudioFile, surahDetailStateData.restartAudio) {
        if (audioState.verseAudioFile != null) {
            surahDetailStateData = surahDetailStateData.copy(runAudio = true)
        }
    }

    // Загружаем чтеца при первом рендере экрана
    LaunchedEffect(Unit) {
        val reciter = quranTextViewModel.getReciterName()
        surahDetailStateData = surahDetailStateData.copy(selectedReciter = reciter)
        if (reciter.isNullOrEmpty()) {
            surahDetailStateData =
                surahDetailStateData.copy(showReciterDialog = true) // Показываем диалог, если чтец не выбран
        }
        quranTextViewModel.getArabicChapter(chapterNumber)
        quranTranslationViewModel.getTranslationForChapter(chapterNumber, "ru.kuliev")
        quranAudioViewModel.getChaptersAudioOfReciter(
            chapterNumber, quranTextViewModel.getReciter().toString()
        )
    }

    playerScreen.Screen(
        audioUrl = audioState.verseAudioFile?.versesAudio?.audio.toString(),
        setIsPlaying = {
            surahDetailStateData = surahDetailStateData.copy(isAudioPlaying = it)
        },
        scrollToAudioElement = {
            scope.launch(Dispatchers.Main) {
                // Прокрутка к нужному элементу
                listState.animateScrollToItem(surahDetailStateData.currentAyah - 1)  // Индекс Ayah может начинаться с 0
            }
        },
        player = player
    )

    Scaffold(bottomBar = {
        ChapterBottomBar(
            colors,
            isPlaying = surahDetailStateData.isAudioPlaying,
            showReciterDialog = { show ->
                surahDetailStateData = surahDetailStateData.copy(showTextBottomSheet = show)
            },
            showSettings = {
                surahDetailStateData = surahDetailStateData.copy(showSettingsBottomSheet = true)
            },
            onClickPlayer = {
                player.onPlayClicked()
            })
    }) { paddingValues ->
        AyaColumn(
            chapterNumber,
            surahDetailStateData.currentAyah,
            isLoading,
            textState,
            translateState,
            colors,
            surahDetailStateData.fontSizeArabic,
            surahDetailStateData.fontSizeRussian,
            surahDetailStateData.runAudio,
            surahDetailStateData.isScrollToAyah,
            listState,
            paddingValues,
        ) { ayahNumber ->
            surahDetailStateData = surahDetailStateData.copy(playWholeChapter = false)
            if (surahDetailStateData.currentAyah == ayahNumber) {
                surahDetailStateData =
                    surahDetailStateData.copy(restartAudio = !surahDetailStateData.restartAudio)
            }
            quranAudioViewModel.getVerseAudioFile(
                "$chapterNumber:$ayahNumber", quranTextViewModel.getReciter().toString()
            )
            surahDetailStateData = surahDetailStateData.copy(currentAyah = ayahNumber)
        }
    }

    // Остальная логика для BottomSheet и выбора шрифта/рецитатора
    if (surahDetailStateData.showSettingsBottomSheet) {
        SettingsBottomSheet(
            showSheet = surahDetailStateData.showSettingsBottomSheet,
            colors = colors,
            selectedReciter = surahDetailStateData.selectedReciter,
            showReciterDialog = { showReciterDialog ->
                surahDetailStateData =
                    surahDetailStateData.copy(showReciterDialog = showReciterDialog)
            },
            showArabic = surahDetailStateData.showArabic,
            onShowArabicChange = { showArabic ->
                surahDetailStateData = surahDetailStateData.copy(showArabic = showArabic)
            },
            showRussian = surahDetailStateData.showRussian,
            onShowRussianChange = { showRussian ->
                surahDetailStateData = surahDetailStateData.copy(showRussian = showRussian)
            },
            onDismiss = {
                surahDetailStateData = surahDetailStateData.copy(showSettingsBottomSheet = false)
            })
    }

    ChooseTextDialog(
        surahDetailStateData.showTextBottomSheet, isDarkTheme = isDarkTheme,
        fontSizeArabic = surahDetailStateData.fontSizeArabic,
        onFontSizeArabicChange = { fontSizeArabic ->
            surahDetailStateData = surahDetailStateData.copy(fontSizeArabic = fontSizeArabic)
        },
        fontSizeRussian = surahDetailStateData.fontSizeRussian,
        onFontSizeRussianChange = { fontSizeRussian ->
            surahDetailStateData = surahDetailStateData.copy(fontSizeRussian = fontSizeRussian)
        },
        onThemeChange = {

        },
    ) {
        surahDetailStateData = surahDetailStateData.copy(showTextBottomSheet = false)
    }

    ChooseReciterDialog(
        surahDetailStateData.showReciterDialog, surahDetailStateData.isFirstSelection, colors
    ) { identifier ->
        surahDetailStateData = surahDetailStateData.copy(showReciterDialog = false)
        if (identifier != null) {
            quranTextViewModel.saveReciter(identifier)
            surahDetailStateData = surahDetailStateData.copy(isFirstSelection = false)
        }
        surahDetailStateData =
            surahDetailStateData.copy(selectedReciter = quranTextViewModel.getReciterName())
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
                1, themeViewModel = ThemeViewModel(
                    SavedStateHandle(), ThemeUseCase(FakeThemeStorage())
                ), quranTextViewModelFactory = QuranTextViewModelFactory(
                    QuranTextUseCaseAqc(
                        FakeQTextRAqc(), FakeQuranStorage(), FakeReciterStorage()
                    )
                ), quranTranslationViewModelFactory = QuranTranslationViewModelFactory(
                    QuranTranslationUseCaseAqc(FakeQTranslationRAqc())
                ), quranAudioViewModelFactory = QuranAudioViewModelFactory(
                    QuranAudioUseCaseAqc(FakeQAudioRAqc())
                ), fakeNavController
            )
        }
    }
}