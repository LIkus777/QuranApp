package com.zaur.features.surah.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.features.surah.fakes.FakeQAudioRAqc
import com.zaur.features.surah.fakes.FakeQTextRAqc
import com.zaur.features.surah.fakes.FakeQTranslationRAqc
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.ModernSurahText
import com.zaur.presentation.ui.QuranAppTheme

@Composable
fun SurahChooseScreen(
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    navController: NavController,
    modifier: Modifier
) {

    LaunchedEffect(Unit) {
        quranTextViewModel.getAllChapters()
    }

    val textState = quranTextViewModel.textUiState.collectAsState().value
    quranAudioViewModel.audioUiState.collectAsState().value
    quranTranslationViewModel.translationUiState.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val chapers = textState.chapters?.chapters ?: emptyList() // Избегаем повторного ?. вызова
        itemsIndexed(chapers) { index, chapter ->
            ModernSurahText(
                number = chapter.number,
                englishName = chapter.englishName,
                arabicName = chapter.name,
                numberOfAyats = chapter.number.toInt(),
                revelationType = chapter.revelationType,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.SurahDetail.createRoute(chapter.number.toInt()))
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SurahScreenPreview() {
    val fakeNavController = rememberNavController()
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahChooseScreen(
                quranTextViewModel = QuranTextViewModel(
                    SavedStateHandle(), QuranTextUseCaseAqc(FakeQTextRAqc(), FakeQuranStorage())
                ), quranAudioViewModel = QuranAudioViewModel(
                    SavedStateHandle(), QuranAudioUseCaseAqc(FakeQAudioRAqc())
                ), quranTranslationViewModel = QuranTranslationViewModel(
                    SavedStateHandle(), QuranTranslationUseCaseAqc(FakeQTranslationRAqc())
                ), modifier = Modifier.padding(innerPadding),
                navController = fakeNavController
            )
        }
    }
}
