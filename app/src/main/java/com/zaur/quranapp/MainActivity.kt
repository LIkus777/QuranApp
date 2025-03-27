package com.zaur.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import com.zaur.features.surah.screen.SurahScreen
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTafsirViewModel
import com.zaur.features.surah.viewmodel.QuranTajweedViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel

class MainActivity : ComponentActivity() {

    private val di by lazy { (application as App).diModule }

    private val quranTextViewModel by lazy { QuranTextViewModel(SavedStateHandle(), di.provideQuranTextUseCase()) }
    private val quranAudioViewModel by lazy { QuranAudioViewModel(SavedStateHandle(), di.provideQuranAudioUseCase()) }
    private val quranTafsirViewModel by lazy { QuranTafsirViewModel(SavedStateHandle(), di.provideQuranTafsirUseCase()) }
    private val quranTajweedViewModel by lazy { QuranTajweedViewModel(SavedStateHandle(), di.provideQuranTajweedUseCase()) }
    private val quranTranslationViewModel by lazy { QuranTranslationViewModel(SavedStateHandle(), di.provideQuranTranslationUseCase()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
            ) {
                SurahScreen(
                    quranTextViewModel,
                    quranAudioViewModel,
                    quranTafsirViewModel,
                    quranTajweedViewModel,
                    quranTranslationViewModel
                )
            }
        }
    }
}
