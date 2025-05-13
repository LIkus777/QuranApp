package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.features.surah.ui_state.aqc.QuranAudioAqcUIState
import com.zaur.features.surah.ui_state.aqc.QuranTextAqcUIState
import com.zaur.features.surah.ui_state.aqc.SurahDetailScreenState
import com.zaur.features.surah.viewmodel.QuranAudioViewModel
import com.zaur.features.surah.viewmodel.QuranTextViewModel
import com.zaur.features.surah.viewmodel.QuranTranslationViewModel
import com.zaur.features.surah.viewmodel.SurahDetailViewModel

/**
* @author Zaur
* @since 2025-05-12
*/

@Composable
fun SurahDetailEffects(
    chapterNumber: Int,
    audioState: QuranAudioAqcUIState,
    surahDetailState: SurahDetailScreenState,
    quranAudioViewModel: QuranAudioViewModel,
    quranTextViewModel: QuranTextViewModel,
    quranTranslationViewModel: QuranTranslationViewModel,
    surahDetailViewModel: SurahDetailViewModel,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(audioState.cacheAudios()) {
        audioState.cacheAudios().let {
            quranAudioViewModel.setCacheAudios(it)
        }
    }

    LaunchedEffect(audioState.chaptersAudioFile()) {
        audioState.chaptersAudioFile().ayahs().let {
            quranAudioViewModel.setAyahs(it)
        }
    }

    LaunchedEffect(audioState.verseAudioFile(), surahDetailState.audioPlayerState().restartAudio()) {
        if (audioState.verseAudioFile() !is VerseAudioAqc.Empty || surahDetailState.audioPlayerState().restartAudio() == true) {
            quranAudioViewModel.onPlayVerse(verse = audioState.verseAudioFile())
        }
    }

    LaunchedEffect(chapterNumber) {
        val reciter = quranAudioViewModel.getReciterName()
        surahDetailViewModel.selectedReciter(reciter.toString())
        if (reciter.isNullOrEmpty()) {
            surahDetailViewModel.showReciterDialog(true)
        }
        quranTextViewModel.getArabicChapter(chapterNumber)
        quranTranslationViewModel.getTranslationForChapter(chapterNumber, "ru.kuliev") //todo
        quranAudioViewModel.downloadToCache(
            chapterNumber,
            quranAudioViewModel.getReciter().toString()
        )
        quranAudioViewModel.getChaptersAudioOfReciter(
            chapterNumber, quranAudioViewModel.getReciter().toString()
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            Log.i("SurahDetailScreen", "SurahDetailScreenContent Lifecycle event: $event")
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            Log.i("SurahDetailScreen", "SurahDetailScreenContent disposed via DisposableEffect")
            quranAudioViewModel.clear()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}