package com.zaur.features.surah.screen.surah_detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zaur.domain.al_quran_cloud.use_case.OfflineUseCase
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTextUseCaseAqc
import com.zaur.domain.al_quran_cloud.use_case.QuranTranslationUseCaseAqc
import com.zaur.domain.storage.theme.ThemeUseCase
import com.zaur.features.surah.fakes.FakeOfflineRepos
import com.zaur.features.surah.fakes.FakeQAudioRAqcCloud
import com.zaur.features.surah.fakes.FakeQAudioRAqcLocal
import com.zaur.features.surah.fakes.FakeQTextRAqcCloud
import com.zaur.features.surah.fakes.FakeQTextRAqcLocal
import com.zaur.features.surah.fakes.FakeQTranslationRAqcCloud
import com.zaur.features.surah.fakes.FakeQTranslationRAqcLocal
import com.zaur.features.surah.fakes.FakeQuranStorage
import com.zaur.features.surah.fakes.FakeReciterManager
import com.zaur.features.surah.fakes.FakeSurahPlayer
import com.zaur.features.surah.fakes.FakeThemeStorage
import com.zaur.features.surah.observables.QuranAudioObservable
import com.zaur.features.surah.observables.QuranTextObservable
import com.zaur.features.surah.observables.QuranTranslationObservable
import com.zaur.features.surah.observables.SurahChooseObservable
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTranslationAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.presentation.ui.QuranAppTheme
import kotlinx.coroutines.launch

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun SurahDetailScreen(
    surahName: String,
    chapterNumber: Int,
    offlineViewModel: OfflineViewModel,
    surahChooseViewModel: SurahChooseViewModel,
    surahDetailViewModel: SurahDetailViewModel,
    themeViewModel: ThemeViewModel,
    quranTextViewModel: QuranTextViewModel,
    quranAudioViewModel: QuranAudioViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    controller: NavHostController,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            SurahChooseMenu(
                themeViewModel = themeViewModel,
                surahChooseViewModel = surahChooseViewModel,
                navController = controller,
                modifier = Modifier.fillMaxSize(),
            )
        }) {
        SurahDetailScreenContent(
            surahName,
            chapterNumber,
            themeViewModel,
            offlineViewModel,
            surahDetailViewModel,
            quranTextViewModel,
            quranAudioViewModel,
            quranTranslationViewModel,
            controller,
            onMenuClick = {
                scope.launch { drawerState.open() }
            })
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            Log.i("SurahDetailScreen", "SurahDetailScreen Lifecycle event: $event")
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            Log.i("SurahDetailScreen", "SurahDetailScreen disposed via DisposableEffect")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

@Preview(showBackground = true)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun SurahDetailScreenPreview() {
    val fakeNavController = rememberNavController()
    val textUseCaseAqc = QuranTextUseCaseAqc.Base(
        FakeQuranStorage(), FakeOfflineRepos(), FakeQTextRAqcLocal(), FakeQTextRAqcCloud()
    )
    val translationUseCaseAqc = QuranTranslationUseCaseAqc.Base(
        FakeOfflineRepos(), FakeQTranslationRAqcLocal(), FakeQTranslationRAqcCloud()
    )
    val audioUseCaseAqc = QuranAudioUseCaseAqc.Base(
        FakeOfflineRepos(), FakeQAudioRAqcLocal(), FakeQAudioRAqcCloud()
    )
    val chooseViewModel = SurahChooseViewModel.Base(
        quranTextUseCaseAqc = textUseCaseAqc,
        observable = SurahChooseObservable.Base(
            QuranTextAqcUIState.Base()
        )
    )
    val offlineViewModel = OfflineViewModel.Base(
        offlineUseCase = OfflineUseCase.Base(FakeOfflineRepos())
    )
    QuranAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SurahDetailScreen(
                surahName = "",
                1,
                offlineViewModel,
                chooseViewModel,
                surahDetailViewModel = SurahDetailViewModel.Base(
                    SurahDetailStateManager.Base(SurahDetailScreenState.Base())
                ),
                themeViewModel = ThemeViewModel.Base(
                    themeUseCase = ThemeUseCase(FakeThemeStorage())
                ),
                quranTextViewModel = QuranTextViewModel.Base(
                    quranTextUseCaseAqc = textUseCaseAqc, observable = QuranTextObservable.Base(
                        QuranTextAqcUIState.Base()
                    )
                ),
                quranTranslationViewModel = QuranTranslationViewModel.Base(
                    quranTranslationUseCaseAqc = translationUseCaseAqc,
                    observable = QuranTranslationObservable.Base(
                        QuranTranslationAqcUIState.Base()
                    )
                ),
                quranAudioViewModel = QuranAudioViewModel.Base(
                    stateManager = SurahDetailStateManager.Base(SurahDetailScreenState.Base()),
                    quranAudioUseCaseAqc = audioUseCaseAqc,
                    surahPlayer = FakeSurahPlayer(),
                    reciterManager = FakeReciterManager(),
                    observable = QuranAudioObservable.Base(
                        QuranAudioAqcUIState.Base()
                    )
                ),
                controller = fakeNavController
            )
        }
    }
}