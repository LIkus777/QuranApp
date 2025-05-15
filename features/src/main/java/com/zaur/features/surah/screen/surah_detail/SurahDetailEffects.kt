package com.zaur.features.surah.screen.surah_detail

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc

/**
 * @author Zaur
 * @since 2025-05-12
 */

@Composable
fun SurahDetailEffects(
    chapterNumber: Int,
    deps: SurahDetailDependencies,
    uiData: SurahDetailUiData,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    with(uiData) {
        with(deps) {

            val pageNumber = quranPageViewModel().getLastReadPagePosition()

            LaunchedEffect(uiData.audioState().cacheAudios()) {
                uiData.audioState().cacheAudios().let {
                    quranAudioViewModel().setCacheAudios(it)
                }
            }

            LaunchedEffect(uiData.audioState().chaptersAudioFile().ayahs()) {
                uiData.audioState().chaptersAudioFile().ayahs().let {
                    quranAudioViewModel().setAyahs(it)
                }
            }

            LaunchedEffect(
                uiData.audioState().verseAudioFile(),
                uiData.surahDetailState().audioPlayerState().restartAudio()
            ) {
                if (audioState().verseAudioFile() !is VerseAudioAqc.Empty || uiData.surahDetailState()
                        .audioPlayerState().restartAudio() == true
                ) {
                    quranAudioViewModel().onPlayVerse(verse = uiData.audioState().verseAudioFile())
                }
            }

            LaunchedEffect(chapterNumber) {
                val reciter = quranAudioViewModel().getReciterName()
                surahDetailViewModel().selectedReciter(reciter.toString())
                if (reciter.isNullOrEmpty()) {
                    surahDetailViewModel().showReciterDialog(true)
                }
                quranPageViewModel().getUthmaniPage(pageNumber)
                quranPageViewModel().getTranslatedPage(pageNumber, "ru.kuliev") //todo
                quranTextViewModel().getArabicChapter(chapterNumber)
                quranTranslationViewModel().getTranslationForChapter(
                    chapterNumber, "ru.kuliev"
                ) //todo
                quranAudioViewModel().downloadToCache(
                    chapterNumber, quranAudioViewModel().getReciter().toString()
                )
                quranAudioViewModel().getChaptersAudioOfReciter(
                    chapterNumber, quranAudioViewModel().getReciter().toString()
                )
            }

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    Log.i("SurahDetailScreen", "SurahDetailScreenContent Lifecycle event: $event")
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    Log.i(
                        "SurahDetailScreen",
                        "SurahDetailScreenContent disposed via DisposableEffect"
                    )
                    quranAudioViewModel().clear()
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
        }
    }
}