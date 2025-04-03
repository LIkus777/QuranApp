package com.zaur.features.surah.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranAppTheme

@Composable
fun SurahDetailScreen(
    chapterNumber: Int,
    themeViewModel: ThemeViewModel,
    quranTextViewModelFactory: QuranTextViewModelFactory,
    quranTranslationViewModelFactory: QuranTranslationViewModelFactory,
    quranAudioViewModelFactory: QuranAudioViewModelFactory,
    controller: NavHostController
) {

    val quranTextViewModel = remember { quranTextViewModelFactory.create() }
    val quranAudioViewModel = remember { quranAudioViewModelFactory.create() }
    val quranTranslationViewModel = remember { quranTranslationViewModelFactory.create() }

    val textState = quranTextViewModel.textUiState.collectAsState().value
    quranAudioViewModel.audioUiState.collectAsState().value
    val translateState = quranTranslationViewModel.translationUiState.collectAsState().value

    var showReciterDialog by remember { mutableStateOf(false) }
    var showTextBottomSheet by remember { mutableStateOf(false) }
    var showSettingsBottomSheet by remember { mutableStateOf(false) }
    var fontSizeArabic by remember { mutableFloatStateOf(24f) }
    var fontSizeRussian by remember { mutableFloatStateOf(18f) }
    var showArabic by remember { mutableStateOf(true) }
    var showRussian by remember { mutableStateOf(true) }
    var selectedReciter by remember { mutableStateOf<String?>(null) }
    var isFirstSelection by remember { mutableStateOf(quranTextViewModel.getReciterName() == null) }

    val isLoading = textState.currentArabicText == null || translateState.translations == null
    val isDarkTheme = themeViewModel.getIsDarkTheme().collectAsState(initial = false).value
    val colors = if (isDarkTheme) DarkThemeColors else LightThemeColors

    // Загружаем чтеца при первом рендере экрана
    LaunchedEffect(Unit) {
        val reciter = quranTextViewModel.getReciterName()
        selectedReciter = reciter
        if (reciter.isNullOrEmpty()) {
            showReciterDialog = true // Показываем диалог, если чтец не выбран
        }
        quranTextViewModel.getArabicChapter(chapterNumber)
        quranTranslationViewModel.getTranslationForChapter(chapterNumber, "ru.kuliev")
    }

    Scaffold(bottomBar = {
        ChapterBottomBar(colors, { show ->
            //showReciterDialog = show
            showTextBottomSheet = show
        }) {
            showSettingsBottomSheet = true
        }
    }) { paddingValues ->
        AyaColumn(
            chapterNumber,
            isLoading,
            textState,
            translateState,
            colors,
            fontSizeArabic,
            fontSizeRussian,
            paddingValues
        )
    }

    if (showSettingsBottomSheet) {
        SettingsBottomSheet(
            showSheet = showSettingsBottomSheet,
            colors = colors,
            selectedReciter = selectedReciter,
            showReciterDialog = { showReciterDialog = it },
            showArabic = showArabic,
            onShowArabicChange = { showArabic = it },
            showRussian = showRussian,
            onShowRussianChange = { showRussian = it },
            onDismiss = { showSettingsBottomSheet = false })
    }

    ChooseTextDialog(
        showTextBottomSheet, isDarkTheme = isDarkTheme,
        onThemeChange = { /*themeViewModel.saveTheme(it)*/ },
        fontSizeArabic = fontSizeArabic,
        onFontSizeArabicChange = { fontSizeArabic = it },
        fontSizeRussian = fontSizeRussian,
        onFontSizeRussianChange = { fontSizeRussian = it },
    ) {
        showTextBottomSheet = false
    }

    ChooseReciterDialog(showReciterDialog, isFirstSelection, colors) { identifier ->
        showReciterDialog = false
        if (identifier != null) {
            quranTextViewModel.saveReciter(identifier)
            isFirstSelection = false
        }
        selectedReciter = quranTextViewModel.getReciterName()
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