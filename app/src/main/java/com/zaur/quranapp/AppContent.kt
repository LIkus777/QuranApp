package com.zaur.quranapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zaur.di.DI
import com.zaur.di.module.MainScreenModule
import com.zaur.features.surah.screen.bookmarks.BookmarksScreen
import com.zaur.features.surah.screen.main.MainBottomAppBar
import com.zaur.features.surah.screen.main.MainScreen
import com.zaur.features.surah.screen.settings.SettingsScreen
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
 * @since 30.05.2025
 */

@Composable
fun AppContent(
    di: DI,
    themeViewModel: ThemeViewModel,
    offlineViewModel: OfflineViewModel,
    mainScreenModule: MainScreenModule,
) {
    val navController = rememberNavController()
    val isDarkTheme = themeViewModel.themeState().collectAsState()

    val dataModule = remember { di.provideDataModule() }
    val translatorManager = remember { dataModule.provideTranslatorManager() }
    val surahDetailModule = remember { di.provideSurahDetailModule() }
    val surahDetailViewModel = remember { surahDetailModule.provideSurahDetailViewModel() }
    val surahPlayerViewModel =
        remember { surahDetailModule.provideSurahPlayerViewModelFactory().create() }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

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
                        navController = navController, mainScreen = { controller ->
                        val mainViewModel = remember {
                            MainViewModelFactory.Base(
                                mainScreenModule.provideMainUseCase(),
                                mainScreenModule.provideReciterManager(),
                                mainScreenModule.provideTranslatorManager()
                            ).create()
                        }
                        MainScreen(
                            controller, themeViewModel, mainViewModel = mainViewModel
                        )
                    }, surahChooseScreen = { controller ->
                        val surahChooseViewModel = remember {
                            surahDetailModule.provideSurahChooseViewModelFactory().create()
                        }
                        SurahChooseScreen(
                            themeViewModel, surahChooseViewModel, controller, onClickPlayer = {
                                surahDetailViewModel.showPlayerBottomSheet(true)
                            })
                    }, bookmarksScreen = {
                        BookmarksScreen()
                    }, settingsScreen = {
                        SettingsScreen()
                    }, surahDetailScreen = { surahNumber, surahName, highlightAyah ->
                        val quranTextViewModel = remember {
                            surahDetailModule.provideQuranTextViewModelFactory().create()
                        }
                        val quranTranslationViewModel = remember {
                            surahDetailModule.provideQuranTranslationViewModelFactory().create()
                        }
                        val screenContentViewModel = remember {
                            surahDetailModule.provideScreenContentViewModelFactory().create()
                        }
                        val quranPageViewModel = remember {
                            surahDetailModule.provideQuranPageViewModelFactory().create()
                        }

                        val deps = SurahDetailDependencies.Base(
                            translatorManager = translatorManager,
                            themeViewModel = themeViewModel,
                            offlineViewModel = offlineViewModel,
                            surahChooseViewModel = surahDetailModule.provideSurahChooseViewModelFactory()
                                .create(),
                            surahDetailViewModel = surahDetailViewModel,
                            quranTextViewModel = quranTextViewModel,
                            surahPlayerViewModel = surahPlayerViewModel,
                            quranTranslationViewModel = quranTranslationViewModel,
                            screenContentViewModel = screenContentViewModel,
                            quranPageViewModel = quranPageViewModel,
                            controller = navController
                        )

                        SurahDetailScreen(
                            surahName = surahName,
                            surahNumber = surahNumber,
                            deps = deps,
                            controller = navController
                        )
                    },
                        // PageDetailScreen
                        pageDetailScreen = { pageNumber, surahNumber, surahName, highlightAyah ->
                            val quranTextViewModel = remember {
                                surahDetailModule.provideQuranTextViewModelFactory().create()
                            }
                            val quranTranslationViewModel = remember {
                                surahDetailModule.provideQuranTranslationViewModelFactory().create()
                            }
                            val screenContentViewModel = remember {
                                surahDetailModule.provideScreenContentViewModelFactory().create()
                            }
                            val quranPageViewModel = remember {
                                surahDetailModule.provideQuranPageViewModelFactory().create()
                            }

                            val deps = SurahDetailDependencies.Base(
                                translatorManager = translatorManager,
                                themeViewModel = themeViewModel,
                                offlineViewModel = offlineViewModel,
                                surahChooseViewModel = surahDetailModule.provideSurahChooseViewModelFactory()
                                    .create(),
                                surahDetailViewModel = surahDetailViewModel,
                                quranTextViewModel = quranTextViewModel,
                                surahPlayerViewModel = surahPlayerViewModel,
                                quranTranslationViewModel = quranTranslationViewModel,
                                screenContentViewModel = screenContentViewModel,
                                quranPageViewModel = quranPageViewModel,
                                controller = navController
                            )

                            SurahDetailScreen(
                                pageNumber = pageNumber,
                                surahName = surahName,
                                surahNumber = surahNumber,
                                deps = deps,
                                controller = navController
                            )
                        },

                        // JuzDetailScreen
                        juzDetailScreen = { juzNumber, surahNumber, surahName, highlightAyah ->
                            val quranTextViewModel = remember {
                                surahDetailModule.provideQuranTextViewModelFactory().create()
                            }
                            val quranTranslationViewModel = remember {
                                surahDetailModule.provideQuranTranslationViewModelFactory().create()
                            }
                            val screenContentViewModel = remember {
                                surahDetailModule.provideScreenContentViewModelFactory().create()
                            }
                            val quranPageViewModel = remember {
                                surahDetailModule.provideQuranPageViewModelFactory().create()
                            }

                            val deps = SurahDetailDependencies.Base(
                                translatorManager = translatorManager,
                                themeViewModel = themeViewModel,
                                offlineViewModel = offlineViewModel,
                                surahChooseViewModel = surahDetailModule.provideSurahChooseViewModelFactory()
                                    .create(),
                                surahDetailViewModel = surahDetailViewModel,
                                quranTextViewModel = quranTextViewModel,
                                surahPlayerViewModel = surahPlayerViewModel,
                                quranTranslationViewModel = quranTranslationViewModel,
                                screenContentViewModel = screenContentViewModel,
                                quranPageViewModel = quranPageViewModel,
                                controller = navController
                            )

                            SurahDetailScreen(
                                juzNumber = juzNumber,
                                surahName = surahName,
                                surahNumber = surahNumber,
                                deps = deps,
                                controller = navController
                            )
                        })
                }
            }

            if (currentRoute != Screen.SurahDetail.route) {
                MainBottomAppBar(
                    currentRoute = currentRoute,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    colors = colors,
                    showQuran = {
                        navController.navigate(Screen.SurahChoose.route) {
                            popUpTo(Screen.MainScreen.route)
                            launchSingleTop = true
                        }
                    },
                    showBookmarks = {
                        navController.navigate(Screen.Bookmarks.route) {
                            popUpTo(Screen.MainScreen.route)
                            launchSingleTop = true
                        }
                    },
                    showSettings = {
                        navController.navigate(Screen.Settings.route) {
                            popUpTo(Screen.MainScreen.route)
                            launchSingleTop = true
                        }
                    })
            }

            PlayerDialogComponentGlobal(
                navController = navController,
                surahDetailViewModel = surahDetailViewModel,
                surahPlayerViewModel = surahPlayerViewModel,
                colors = colors,
            )
        }
    }
}
