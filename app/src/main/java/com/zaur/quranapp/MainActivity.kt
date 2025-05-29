package com.zaur.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zaur.features.surah.screen.main.MainBottomAppBar
import com.zaur.features.surah.screen.main.MainScreen
import com.zaur.features.surah.screen.surah_choose.SurahChooseScreen
import com.zaur.features.surah.screen.surah_detail.SurahDetailDependencies
import com.zaur.features.surah.screen.surah_detail.SurahDetailScreen
import com.zaur.features.surah.screen.surah_detail.dialogs.PlayerDialogComponentGlobal
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.MainViewModelFactory
import com.zaur.navigation.QuranNavGraph
import com.zaur.navigation.Screen
import com.zaur.presentation.ui.DarkThemeColors
import com.zaur.presentation.ui.LightThemeColors
import com.zaur.presentation.ui.QuranAppTheme

/**
 * @author Zaur
 * @since 2025-05-12
 */

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule() }
    private val mainScreenModule by lazy { di.provideMainScreenModule() }
    private val dataModule by lazy { di.provideDataModule() }

    private val themeViewModel by lazy { ThemeViewModel.Base(themeUseCase = mainScreenModule.provideThemeUseCase()) }
    private val offlineViewModel by lazy { OfflineViewModel.Base(offlineUseCase = dataModule.provideOfflineUseCase()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val isDarkTheme = themeViewModel.themeState().collectAsState()

            val surahDetailModule = remember { di.provideSurahDetailModule() }
            val surahDetailViewModel = remember { surahDetailModule.provideSurahDetailViewModel() }
            val quranAudioViewModel =
                remember { surahDetailModule.provideSurahPlayerViewModelFactory().create() }

            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination?.route
            val showBottomBar = currentDestination != Screen.SurahDetail.route

            val colors = if (isDarkTheme.value.isDarkTheme) DarkThemeColors else LightThemeColors

            QuranAppTheme(darkTheme = isDarkTheme.value.isDarkTheme) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()  // учесть статус-/навигационные бары
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .systemBarsPadding()
                        ) {
                            QuranNavGraph(
                                navController = navController,
                                mainScreen = { controller ->
                                    val mainViewModel = remember {
                                        MainViewModelFactory.Base(
                                            mainScreenModule.provideMainUseCase(),
                                            mainScreenModule.provideReciterManager()
                                        ).create()
                                    }
                                    MainScreen(
                                        controller, themeViewModel, mainViewModel = mainViewModel
                                    )
                                },
                                surahChooseScreen = { controller ->
                                    val surahChooseViewModel = remember {
                                        surahDetailModule.provideSurahChooseViewModelFactory()
                                            .create()
                                    }
                                    SurahChooseScreen(
                                        themeViewModel,
                                        surahChooseViewModel,
                                        controller,
                                        contentPadding = innerPadding,
                                        onClickPlayer = {
                                            surahDetailViewModel.showPlayerBottomSheet(true)
                                        })
                                },
                                surahDetailScreen = { number, name, controller ->
                                    val quranTextViewModel = remember {
                                        surahDetailModule.provideQuranTextViewModelFactory()
                                            .create()
                                    }
                                    val quranTranslationViewModel = remember {
                                        surahDetailModule.provideQuranTranslationViewModelFactory()
                                            .create()
                                    }
                                    val screenContentViewModel = remember {
                                        surahDetailModule.provideScreenContentViewModelFactory()
                                            .create()
                                    }
                                    val quranPageViewModel = remember {
                                        surahDetailModule.provideQuranPageViewModelFactory()
                                            .create()
                                    }

                                    val deps = SurahDetailDependencies.Base(
                                        themeViewModel = themeViewModel,
                                        offlineViewModel = offlineViewModel,
                                        surahChooseViewModel = surahDetailModule.provideSurahChooseViewModelFactory()
                                            .create(),
                                        surahDetailViewModel = surahDetailViewModel,
                                        quranTextViewModel = quranTextViewModel,
                                        surahPlayerViewModel = quranAudioViewModel,
                                        quranTranslationViewModel = quranTranslationViewModel,
                                        screenContentViewModel = screenContentViewModel,
                                        quranPageViewModel = quranPageViewModel,
                                        controller = navController
                                    )

                                    SurahDetailScreen(name, number, deps, navController)
                                })
                        }
                    }
                    if (showBottomBar) {
                        MainBottomAppBar(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .windowInsetsPadding(WindowInsets.navigationBars), colors = colors,
                            //navController = navController,
                            showQuran = {

                            }, showBookmarks = {

                            }, showSettings = {

                            })
                    }

                    PlayerDialogComponentGlobal(
                        surahDetailViewModel = surahDetailViewModel,
                        surahPlayerViewModel = quranAudioViewModel,
                        colors = colors,
                    )
                }
            }
        }
    }
}