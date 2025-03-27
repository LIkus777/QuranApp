package com.zaur.features.surah.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.zaur.domain.use_case.QuranAudioUseCase
import com.zaur.domain.use_case.QuranTafsirUseCase
import com.zaur.domain.use_case.QuranTajweedUseCase
import com.zaur.domain.use_case.QuranTextUseCase
import com.zaur.domain.use_case.QuranTranslationUseCase
import com.zaur.features.surah.fakes.FakeQAudioR
import com.zaur.features.surah.fakes.FakeQTafsirR
import com.zaur.features.surah.fakes.FakeQTajweedR
import com.zaur.features.surah.fakes.FakeQTextR
import com.zaur.features.surah.fakes.FakeQTranslationR
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTafsirViewModel
import com.zaur.features.surah.viewmodel.QuranTajweedViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel


@Composable
fun SurahScreen(
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTafsirViewModel: QuranTafsirViewModel,
    quranTajweedViewModel: QuranTajweedViewModel,
    quranTranslationViewModel: QuranTranslationViewModel
) {

    quranTextViewModel.textUiState.collectAsState().value
    quranAudioViewModel.audioUiState.collectAsState().value
    quranTafsirViewModel.tafsirUiState.collectAsState().value
    quranTajweedViewModel.tajweedUiState.collectAsState().value
    quranTranslationViewModel.translationUiState.collectAsState().value


}

@Preview(showBackground = true)
@Composable
fun SurahScreenPreview() {
    SurahScreen(
        quranTextViewModel = QuranTextViewModel(SavedStateHandle(), QuranTextUseCase(FakeQTextR())),
        quranAudioViewModel = QuranAudioViewModel(
            SavedStateHandle(), QuranAudioUseCase(FakeQAudioR())
        ),
        quranTafsirViewModel = QuranTafsirViewModel(
            SavedStateHandle(), QuranTafsirUseCase(
                FakeQTafsirR()
            )
        ),
        quranTajweedViewModel = QuranTajweedViewModel(
            SavedStateHandle(), QuranTajweedUseCase(
                FakeQTajweedR()
            )
        ),
        quranTranslationViewModel = QuranTranslationViewModel(
            SavedStateHandle(), QuranTranslationUseCase(FakeQTranslationR())
        )
    )
}
