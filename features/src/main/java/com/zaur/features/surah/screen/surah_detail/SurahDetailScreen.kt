package com.zaur.features.surah.screen.surah_detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL
import kotlin.jvm.java

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SurahDetailScreen(
    pageNumber: Int = 0,
    juzNumber: Int = 0,
    surahName: String,
    surahNumber: Int,
    deps: SurahDetailDependencies,
    controller: NavHostController,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    ModalNavigationDrawer(
        drawerState = drawerState, drawerContent = {
            SurahChooseMenu(
                currentSurahNumber = surahNumber,
                themeViewModel = deps.themeViewModel(),
                surahChooseViewModel = deps.surahChooseViewModel(),
                navController = deps.controller(),
            )
        }, gesturesEnabled = drawerState.isOpen
    ) {
        SurahDetailScreenContent(
            surahName, surahNumber, deps, controller, onMenuClick = {
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
fun SurahDetailScreenPreview() {/*rememberNavController()
    val textUseCaseAqc = QuranTextUseCase.Base(
        FakeQuranStorage(), FakeOfflineRepos(), FakeQTextRLocal(), FakeQTextRCloud()
    )
    QuranTranslationUseCase.Base(
        FakeOfflineRepos(), FakeQTranslationRLocal(), FakeQTranslationRCloud()
    )
    QuranAudioUseCase.Base(
        FakeQuranStorage(), FakeOfflineRepos(), FakeQAudioRLocal(), FakeQAudioRCloud()
    )
    SurahChooseViewModel.Base(
        quranTextUseCase = textUseCaseAqc, observable = SurahChooseObservable.Base(
            QuranTextUIState.Base()
        )
    )
    OfflineViewModel.Base(
        offlineUseCase = OfflineUseCase.Base(FakeOfflineRepos())
    )*//*
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
                    quranTextUseCase = textUseCaseAqc, observable = QuranTextObservable.Base(
                        QuranTextAqcUIState.Base()
                    )
                ),
                quranTranslationViewModel = QuranTranslationViewModel.Base(
                    quranTranslationUseCase = translationUseCaseAqc,
                    observable = QuranTranslationObservable.Base(
                        QuranTranslationAqcUIState.Base()
                    )
                ),
                quranAudioViewModel = QuranAudioViewModel.Base(
                    stateManager = SurahDetailStateManager.Base(SurahDetailScreenState.Base()),
                    quranAudioUseCase = audioUseCaseAqc,
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
*/
}