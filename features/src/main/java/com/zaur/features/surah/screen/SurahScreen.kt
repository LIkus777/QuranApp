package com.zaur.features.surah.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.zaur.domain.apiV4.use_case.QuranAudioUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTafsirUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTajweedUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTextUseCaseV4
import com.zaur.domain.apiV4.use_case.QuranTranslationUseCaseV4
import com.zaur.features.surah.fakes.FakeQAudioR
import com.zaur.features.surah.fakes.FakeQTafsirR
import com.zaur.features.surah.fakes.FakeQTajweedR
import com.zaur.features.surah.fakes.FakeQTextR
import com.zaur.features.surah.fakes.FakeQTranslationR
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTafsirViewModel
import com.zaur.features.surah.viewmodel.QuranTajweedViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.presentation.ui.QuranAppTheme


@Composable
fun SurahScreen(
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTafsirViewModel: QuranTafsirViewModel,
    quranTajweedViewModel: QuranTajweedViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    modifier: Modifier
) {

    val textState = quranTextViewModel.textUiState.collectAsState().value
    val audioState = quranAudioViewModel.audioUiState.collectAsState().value
    val tafsirState = quranTafsirViewModel.tafsirUiState.collectAsState().value
    val tajweedState = quranTajweedViewModel.tajweedUiState.collectAsState().value
    val translationState = quranTranslationViewModel.translationUiState.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(textState.chapters) { index, lang ->
            Text("${textState.chapters[index].nameSimple}")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SurahScreenPreview() {
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahScreen(
                quranTextViewModel = QuranTextViewModel(
                    SavedStateHandle(),
                    QuranTextUseCaseV4(FakeQTextR(), FakeQuranStorage())
                ),
                quranAudioViewModel = QuranAudioViewModel(
                    SavedStateHandle(), QuranAudioUseCaseV4(FakeQAudioR())
                ),
                quranTafsirViewModel = QuranTafsirViewModel(
                    SavedStateHandle(), QuranTafsirUseCaseV4(
                        FakeQTafsirR()
                    )
                ),
                quranTajweedViewModel = QuranTajweedViewModel(
                    SavedStateHandle(), QuranTajweedUseCaseV4(
                        FakeQTajweedR()
                    )
                ),
                quranTranslationViewModel = QuranTranslationViewModel(
                    SavedStateHandle(), QuranTranslationUseCaseV4(FakeQTranslationR())
                ),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
