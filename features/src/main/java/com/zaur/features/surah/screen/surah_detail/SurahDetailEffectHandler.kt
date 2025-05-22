package com.zaur.features.surah.screen.surah_detail


/**
 * @author Zaur
 * @since 17.05.2025
 */

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SurahDetailEffectHandler {

    @Composable
    fun Handle()

    @Composable
    fun HandleAudioCache()

    @Composable
    fun HandleAyahs()

    @Composable
    fun HandlePlayVerse()

    @Composable
    fun HandleReciter()

    @Composable
    fun HandleInitialLoad(pageNumber: Int)

    @Composable
    fun HandleLifecycleLogging(lifecycleOwner: androidx.lifecycle.LifecycleOwner)

    class Base(
        private val chapterNumber: Int,
        private val uiData: SurahDetailUiData,
        private val deps: SurahDetailDependencies,
        private val controller: NavHostController,
    ) : SurahDetailEffectHandler {

        private val quranAudio = deps.quranAudioViewModel()
        private val quranPage = deps.quranPageViewModel()
        private val quranText = deps.quranTextViewModel()
        private val quranTranslation = deps.quranTranslationViewModel()
        private val surahDetail = deps.surahDetailViewModel()

        @Composable
        override fun Handle() {
            val lifecycleOwner = LocalLifecycleOwner.current
            val pageNumber by remember {
                derivedStateOf { quranPage.getLastReadPagePosition() }
            }

            HandleAudioCache()
            HandleAyahs()
            HandlePlayVerse()
            HandleReciter()
            HandleInitialLoad(pageNumber)
            HandleLifecycleLogging(lifecycleOwner)
        }

        @Composable
        override fun HandleAudioCache() {
            val cached = uiData.audioState().cacheAudios()
            LaunchedEffect(cached) {
                if (!cached.isNullOrEmpty()) {
                    Log.d("TAG", "HandleAudioCache: cache - $cached")
                    quranAudio.setCacheAudios(cached)
                } else {
                    // показать полосу загрузки — TODO
                }
            }
        }

        @Composable
        override fun HandleAyahs() {
            val ayahs = uiData.audioState().chaptersAudioFile().ayahs()
            LaunchedEffect(ayahs) {
                quranAudio.setAyahs(ayahs)
            }
        }

        @Composable
        override fun HandlePlayVerse() {
            val verse = uiData.audioState().verseAudioFile()
            val restart = uiData.surahDetailState().audioPlayerState().restartAudio()
            LaunchedEffect(verse, restart) {
                if (verse !is VerseAudioAqc.Empty || restart == true) {
                    quranAudio.onPlayVerse(verse)
                }
            }
        }

        @Composable
        override fun HandleReciter() {
            val reciter = uiData.surahDetailState().reciterState().currentReciter()
            val reciterName = uiData.surahDetailState().reciterState().currentReciterName()
            LaunchedEffect(reciter, reciterName) {
                quranAudio.downloadToCache(chapterNumber, reciter)
            }
        }

        @Composable
        override fun HandleInitialLoad(pageNumber: Int) {
            LaunchedEffect(uiData.surahDetailState().audioPlayerState().currentSurahNumber()) {
                val currentSurahNumber =
                    uiData.surahDetailState().audioPlayerState().currentSurahNumber()
                if (chapterNumber != currentSurahNumber && currentSurahNumber != 0) {
                    val surahName =
                        uiData.textState().chapters()[currentSurahNumber - 1].englishName()
                    controller.navigate(
                        Screen.SurahDetail.createRoute(
                            currentSurahNumber, surahName
                        )
                    )
                }
            }

            LaunchedEffect(chapterNumber) {

                val reciter =
                    quranAudio.getReciter() ?: throw IllegalStateException("Нету ресайтера")
                val reciterName = quranAudio.getReciterName()
                    ?: throw IllegalStateException("Нету ресайтера name")
                surahDetail.selectedReciter(reciter, reciterName)
                if (reciter.isNullOrEmpty()) {
                    surahDetail.showReciterDialog(true)
                }

                // Запросы с безопасным фоновым выполнением
                withContext(Dispatchers.IO) {
                    quranPage.getUthmaniPage(pageNumber)
                    quranPage.getTranslatedPage(pageNumber, "ru.kuliev")
                    quranText.getAllChapters()
                    quranText.getArabicChapter(chapterNumber)
                    quranTranslation.getTranslationForChapter(chapterNumber, "ru.kuliev")
                    quranAudio.downloadToCache(chapterNumber, reciter)
                    quranAudio.getChaptersAudioOfReciter(chapterNumber, reciter)
                }
            }
        }

        @Composable
        override fun HandleLifecycleLogging(lifecycleOwner: androidx.lifecycle.LifecycleOwner) {
            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    Log.i("SurahDetailScreen", "SurahDetailScreenContent Lifecycle event: $event")
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    Log.i("SurahDetailScreen", "SurahDetailScreenContent disposed")
                    quranAudio.clear()
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
        }
    }
}
