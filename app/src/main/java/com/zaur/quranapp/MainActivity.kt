package com.zaur.quranapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.SavedStateHandle
import com.zaur.features.surah.screen.SurahScreen
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTafsirViewModel
import com.zaur.features.surah.viewmodel.QuranTajweedViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel

class MainActivity : ComponentActivity() {

    private val quranTextViewModel by lazy { QuranTextViewModel(SavedStateHandle()) }
    private val quranAudioViewModel by lazy { QuranAudioViewModel(SavedStateHandle()) }
    private val quranTafsirViewModel by lazy { QuranTafsirViewModel(SavedStateHandle()) }
    private val quranTajweedViewModel by lazy { QuranTajweedViewModel(SavedStateHandle()) }
    private val quranTranslationViewModel by lazy { QuranTranslationViewModel(SavedStateHandle()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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

