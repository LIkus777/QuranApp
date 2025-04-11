package com.zaur.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.rememberNavController
import com.zaur.features.surah.screen.MainScreen
import com.zaur.features.surah.screen.SurahChooseScreen
import com.zaur.features.surah.screen.SurahDetailScreen
import com.zaur.features.surah.viewmodel.SurahChooseViewModel
import com.zaur.features.surah.viewmodel.ThemeViewModel
import com.zaur.features.surah.viewmodel.factory.QuranAudioViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTextViewModelFactory
import com.zaur.features.surah.viewmodel.factory.QuranTranslationViewModelFactory
import com.zaur.features.surah.viewmodel.factory.SurahChooseViewModelFactory
import com.zaur.navigation.QuranNavGraph

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule }

    private val themeViewModel by lazy {
        ThemeViewModel(
            SavedStateHandle(),
            di.provideThemeUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            //QuranAppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                QuranNavGraph(navController = navController,
                    mainScreen = {
                    MainScreen()
                }, surahChooseScreen = {
                    SurahChooseScreen(
                        themeViewModel,
                        surahChooseViewModelFactory = SurahChooseViewModelFactory(di.provideQuranTextUseCaseAqc()),
                        navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }, surahDetailScreen = { surahNumber, controller ->
                    SurahDetailScreen(
                        surahNumber,
                        themeViewModel,
                        quranTextViewModelFactory = QuranTextViewModelFactory(di.provideQuranTextUseCaseAqc()),
                        quranTranslationViewModelFactory = QuranTranslationViewModelFactory(di.provideQuranTranslationUseCaseAqc()),
                        quranAudioViewModelFactory = QuranAudioViewModelFactory(di.provideQuranAudioUseCaseAqc()),
                        controller
                    )
                })
            }
            //}
        }
    }
}
