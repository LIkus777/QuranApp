package com.zaur.features.surah.screen.surah_detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory
import com.zaur.presentation.ui.QuranAppTheme
import kotlinx.coroutines.launch

@Composable
fun SurahDetailScreen(
    surahName: String,
    chapterNumber: Int,
    chooseViewModelFactory: SurahChooseViewModelFactory.Base,
    surahDetailViewModel: SurahDetailViewModel,
    themeViewModel: ThemeViewModel,
    quranTextViewModelFactory: QuranTextViewModelFactory,
    quranTranslationViewModelFactory: QuranTranslationViewModelFactory,
    quranAudioViewModelFactory: QuranAudioViewModelFactory,
    controller: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            SurahChooseMenu(
                themeViewModel = themeViewModel,
                surahChooseViewModelFactory = chooseViewModelFactory,
                navController = controller,
                modifier = Modifier.fillMaxSize(),
            )
        }) {
        SurahDetailScreenContent(
            surahName,
            chapterNumber,
            themeViewModel,
            surahDetailViewModel,
            quranTextViewModelFactory,
            quranAudioViewModelFactory,
            quranTranslationViewModelFactory,
            controller,
            onMenuClick = {
                scope.launch { drawerState.open() }
            })
    }
}

@Preview(showBackground = true)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SurahDetailScreenPreview() {
    val fakeNavController = rememberNavController()
    val chooseViewModelFactory = SurahChooseViewModelFactory.Base(
        quranTextUseCaseAqc = QuranTextUseCaseAqc.Base(
            FakeQTextRAqc(), FakeQuranStorage()
        )
    )
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahDetailScreen(
                surahName = "",
                1,
                chooseViewModelFactory,
                surahDetailViewModel = SurahDetailViewModel.Base(
                    SurahDetailStateManager.Base(SurahDetailScreenState())
                ),
                themeViewModel = ThemeViewModel.Base(
                    SavedStateHandle(), ThemeUseCase(FakeThemeStorage())
                ),
                quranTextViewModelFactory = QuranTextViewModelFactory.Base(
                    quranTextUseCaseAqc = QuranTextUseCaseAqc.Base(
                        FakeQTextRAqc(), FakeQuranStorage()
                    )
                ),
                quranTranslationViewModelFactory = QuranTranslationViewModelFactory.Base(
                    quranTranslationUseCaseAqc = QuranTranslationUseCaseAqc.Base(
                        FakeQTranslationRAqc()
                    )
                ),
                quranAudioViewModelFactory = QuranAudioViewModelFactory.Base(
                    stateManager = SurahDetailStateManager.Base(SurahDetailScreenState()),
                    quranAudioUseCaseAqc = QuranAudioUseCaseAqc.Base(
                        FakeQAudioRAqc(), FakeReciterStorage()
                    )
                ),
                fakeNavController
            )
        }
    }
}