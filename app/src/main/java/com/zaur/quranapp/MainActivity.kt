package com.zaur.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.rememberNavController
import com.zaur.features.surah.screen.MainScreen
import com.zaur.features.surah.screen.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_choose.SurahChooseScreen
import com.zaur.features.surah.screen.surah_detail.SurahDetailScreen
import com.zaur.features.surah.viewmodel.SurahDetailViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory
import com.zaur.navigation.QuranNavGraph

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule }

    private val themeViewModel by lazy {
        ThemeViewModel.Base(
            SavedStateHandle(), di.provideThemeUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val chooseViewModelFactory =
                SurahChooseViewModelFactory.Base(quranTextUseCaseAqc = di.provideQuranTextUseCaseAqc())
            //QuranAppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                QuranNavGraph(navController = navController, mainScreen = {
                    MainScreen(navController)
                }, surahChooseScreen = {
                    SurahChooseScreen(
                        themeViewModel,
                        surahChooseViewModelFactory = chooseViewModelFactory,
                        navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }, surahDetailScreen = { surahNumber, surahName, controller ->
                    val stateManager = remember { SurahDetailStateManager.Base() }
                    SurahDetailScreen(
                        surahName,
                        surahNumber,
                        chooseViewModelFactory,
                        surahDetailViewModel = SurahDetailViewModel.Base(stateManager),
                        themeViewModel = themeViewModel,
                        quranTextViewModelFactory = QuranTextViewModelFactory.Base(
                            quranTextUseCaseAqc = di.provideQuranTextUseCaseAqc()
                        ),
                        quranTranslationViewModelFactory = QuranTranslationViewModelFactory.Base(
                            quranTranslationUseCaseAqc = di.provideQuranTranslationUseCaseAqc()
                        ),
                        quranAudioViewModelFactory = QuranAudioViewModelFactory.Base(
                            context = this,
                            stateManager = stateManager,
                            quranAudioUseCaseAqc = di.provideQuranAudioUseCaseAqc()
                        ),
                        controller
                    )
                })
            }
            //}
        }
    }
}
