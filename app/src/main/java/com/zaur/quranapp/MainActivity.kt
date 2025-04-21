package com.zaur.quranapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.rememberNavController
import com.zaur.features.surah.screen.main.MainScreen
import com.zaur.features.surah.screen.surah_choose.SurahChooseScreen
import com.zaur.features.surah.screen.surah_detail.SurahDetailScreen
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.MainViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory
import com.zaur.navigation.QuranNavGraph
import com.zaur.presentation.ui.QuranAppTheme

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule() }

    private val mainScreenModule by lazy {
        di.provideMainScreenModule()
    }

    private val themeViewModel by lazy {
        ThemeViewModel.Base(
            SavedStateHandle(), mainScreenModule.provideThemeUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val surahChooseModule = remember {
                di.provideSurahChooseModule()
            }
            val chooseViewModelFactory = SurahChooseViewModelFactory.Base(
                quranTextUseCaseAqc = surahChooseModule.provideQuranTextUseCaseAqc()
            )
            val isDarkTheme = themeViewModel.getIsDarkTheme().collectAsState(initial = false)
            Log.i("TAGGGG", "AyahItem: isDarkTheme ${isDarkTheme.value}")
            QuranAppTheme(darkTheme = isDarkTheme.value) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuranNavGraph(navController = navController, mainScreen = {
                        MainScreen(
                            navController,
                            themeViewModel,
                            mainViewModelFactory = MainViewModelFactory.Base(
                                mainScreenModule.provideMainUseCase(),
                                mainScreenModule.provideReciterManager()
                            )
                        )
                    }, surahChooseScreen = {

                        SurahChooseScreen(
                            themeViewModel,
                            surahChooseViewModelFactory = chooseViewModelFactory,
                            navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }, surahDetailScreen = { surahNumber, surahName, controller ->
                        val surahDetailModule = remember {
                            di.provideSurahDetailModule()
                        }
                        SurahDetailScreen(
                            surahName,
                            surahNumber,
                            chooseViewModelFactory,
                            surahDetailViewModel = surahDetailModule.provideSurahDetailViewModel(),
                            themeViewModel = themeViewModel,
                            quranTextViewModelFactory = surahDetailModule.provideQuranTextViewModelFactory(),
                            quranTranslationViewModelFactory = surahDetailModule.provideQuranTranslationViewModelFactory(),
                            quranAudioViewModelFactory = surahDetailModule.provideQuranAudioViewModelFactory(),
                            controller,
                        )
                    })
                }
            }
        }
    }
}
