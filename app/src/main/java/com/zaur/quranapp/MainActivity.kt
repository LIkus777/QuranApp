package com.zaur.quranapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zaur.features.surah.screen.main.MainScreen
import com.zaur.features.surah.screen.surah_choose.SurahChooseScreen
import com.zaur.features.surah.screen.surah_detail.SurahDetailScreen
import com.zaur.features.surah.viewmodel.OfflineViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.MainViewModelFactory
import com.zaur.navigation.QuranNavGraph
import com.zaur.presentation.ui.QuranAppTheme

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
            val isDarkTheme = themeViewModel.getIsDarkTheme()
            Log.i("TAGGGG", "AyahItem: isDarkTheme ${isDarkTheme}")
            QuranAppTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuranNavGraph(navController = navController, mainScreen = { controller ->
                        MainScreen(
                            controller,
                            themeViewModel,
                            mainViewModelFactory = MainViewModelFactory.Base(
                                mainScreenModule.provideMainUseCase(),
                                mainScreenModule.provideReciterManager()
                            )
                        )
                    }, surahChooseScreen = { controller ->
                        val surahDetailModule = remember { di.provideSurahDetailModule() }
                        val surahChooseViewModel = remember { surahDetailModule.provideSurahChooseViewModelFactory().create() }
                        SurahChooseScreen(
                            themeViewModel,
                            surahChooseViewModel,
                            controller,
                        )
                    }, surahDetailScreen = { surahNumber, surahName, controller ->
                        val surahDetailModule = remember { di.provideSurahDetailModule() }
                        val surahDetailViewModel = remember { surahDetailModule.provideSurahDetailViewModel() }
                        val quranTextViewModel = remember { surahDetailModule.provideQuranTextViewModelFactory().create() }
                        val quranAudioViewModel = remember { surahDetailModule.provideQuranAudioViewModelFactory().create() }
                        val surahChooseViewModel = remember { surahDetailModule.provideSurahChooseViewModelFactory().create() }
                        val quranTranslationViewModel = remember { surahDetailModule.provideQuranTranslationViewModelFactory().create() }
                        SurahDetailScreen(
                            surahName,
                            surahNumber,
                            offlineViewModel,
                            surahChooseViewModel,
                            surahDetailViewModel = surahDetailViewModel,
                            themeViewModel = themeViewModel,
                            quranTextViewModel = quranTextViewModel,
                            quranAudioViewModel = quranAudioViewModel,
                            quranTranslationViewModel = quranTranslationViewModel,
                            controller,
                        )
                    })
                }
            }
        }
    }
}