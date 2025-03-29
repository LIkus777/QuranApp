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
import com.zaur.features.surah.screen.SurahScreen
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTafsirViewModel
import com.zaur.features.surah.viewmodel.QuranTajweedViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
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
            QuranAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SurahScreen(
                        quranTextViewModel,
                        quranAudioViewModel,
                        quranTranslationViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
