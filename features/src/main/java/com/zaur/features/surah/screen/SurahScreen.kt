package com.zaur.features.surah.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTafsirViewModel
import com.zaur.features.surah.viewmodel.QuranTajweedViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.presentation.ui.theme.QuranAppTheme

@Composable
fun SurahScreen(
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTafsirViewModel: QuranTafsirViewModel,
    quranTajweedViewModel: QuranTajweedViewModel,
    quranTranslationViewModel: QuranTranslationViewModel
) {

    val textState = quranTextViewModel.textUiState.collectAsState().value
    val audioState = quranAudioViewModel.audioUiState.collectAsState().value
    val tafsirState = quranTafsirViewModel.tafsirUiState.collectAsState().value
    val tajweedState = quranTajweedViewModel.tajweedUiState.collectAsState().value
    val translationState = quranTranslationViewModel.translationUiState.collectAsState().value

}

@Preview(showBackground = true)
@Composable
fun SurahScreenPreview() {
    QuranAppTheme {
        SurahScreen(
            quranTextViewModel = QuranTextViewModel(SavedStateHandle()),
            quranAudioViewModel = QuranAudioViewModel(SavedStateHandle()),
            quranTafsirViewModel = QuranTafsirViewModel(SavedStateHandle()),
            quranTajweedViewModel = QuranTajweedViewModel(SavedStateHandle()),
            quranTranslationViewModel = QuranTranslationViewModel(SavedStateHandle())
        )
    }
}
