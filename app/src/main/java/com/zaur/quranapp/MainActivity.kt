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
import com.zaur.features.surah.screen.SurahChooseScreen
import com.zaur.features.surah.screen.SurahDetailScreen
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.navigation.QuranNavGraph
import com.zaur.quranapp.theme.QuranAppTheme

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule }

    private val quranTextViewModel by lazy { QuranTextViewModel(SavedStateHandle(), di.provideQuranTextUseCaseAqc()) }
    private val quranAudioViewModel by lazy { QuranAudioViewModel(SavedStateHandle(), di.provideQuranAudioUseCaseAqc()) }
    private val quranTranslationViewModel by lazy { QuranTranslationViewModel(SavedStateHandle(), di.provideQuranTranslationUseCaseAqc()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            QuranAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuranNavGraph(navController = navController, surahChooseScreen = {
                        SurahChooseScreen(
                            quranTextViewModel,
                            quranAudioViewModel,
                            quranTranslationViewModel,
                            navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }, surahDetailScreen = { surahNumber, controller ->
                        SurahDetailScreen(surahNumber, controller)
                    })
                }
            }
        }
    }
}
