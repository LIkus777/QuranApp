package com.zaur.features.surah.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.zaur.features.surah.fakes.FakeQAudioRAqc
import com.zaur.features.surah.fakes.FakeQTextRAqc
import com.zaur.features.surah.fakes.FakeQTranslationRAqc
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.fakes.FakeReciterStorage
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.presentation.ui.QuranAppTheme

@Composable
fun SurahDetailScreen(
    surahNumber: Int,
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    controller: NavHostController
) {

    quranTextViewModel.textUiState.collectAsState().value
    quranAudioViewModel.audioUiState.collectAsState().value
    quranTranslationViewModel.translationUiState.collectAsState().value

    var showReciterDialog by remember { mutableStateOf(false) }
    var selectedReciter by remember { mutableStateOf<String?>(null) }

    // Загружаем чтеца при первом рендере экрана
    LaunchedEffect(Unit) {
        val reciter = quranTextViewModel.getReciterName()
        selectedReciter = reciter
        if (reciter.isNullOrEmpty()) {
            showReciterDialog = true // Показываем диалог, если чтец не выбран
        }
    }

    ChooseReciterDialog(showReciterDialog) { identifier ->
        showReciterDialog = false
        quranTextViewModel.saveReciter(identifier.toString())
        selectedReciter = identifier
    }

    Text(selectedReciter.toString())
}

@Preview(showBackground = true)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SurahDetailScreenPreview() {
    val fakeNavController = rememberNavController()
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahDetailScreen(
                1, quranTextViewModel = QuranTextViewModel(
                    SavedStateHandle(), QuranTextUseCaseAqc(
                        FakeQTextRAqc(), FakeQuranStorage(), FakeReciterStorage()
                    )
                ), quranAudioViewModel = QuranAudioViewModel(
                    SavedStateHandle(), QuranAudioUseCaseAqc(FakeQAudioRAqc())
                ), quranTranslationViewModel = QuranTranslationViewModel(
                    SavedStateHandle(), QuranTranslationUseCaseAqc(FakeQTranslationRAqc())
                ), fakeNavController
            )
        }
    }
}