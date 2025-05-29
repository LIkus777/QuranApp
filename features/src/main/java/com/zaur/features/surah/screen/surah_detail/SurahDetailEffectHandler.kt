package com.zaur.features.surah.screen.surah_detail

/**
 * @author Zaur
 * @since 17.05.2025
 */

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SurahNavigationCallback {
    fun navigate(surahNumber: Int)
}

interface SurahDetailEffectHandler {

    @Composable
    fun Handle()

    @Composable
    fun HandleAudioCache()

    @Composable
    fun HandlePlayVerse()

    @Composable
    fun HandleReciter()

    @Composable
    fun HandleAyah()

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

        private val surahPlayerVm = deps.surahPlayerViewModel()
        private val quranPageVm = deps.quranPageViewModel()
        private val quranTextVm = deps.quranTextViewModel()
        private val quranTranslationVm = deps.quranTranslationViewModel()
        private val surahDetailVm = deps.surahDetailViewModel()

        @Composable
        override fun Handle() {
            val lifecycleOwner = LocalLifecycleOwner.current
            val pageNumber by remember {
                derivedStateOf { quranPageVm.getLastReadPagePosition() }
            }

            Log.i("bug", "Handle CALLED")

            HandleAudioCache()
            HandlePlayVerse()
            HandleAyah()
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
                    surahPlayerVm.setCacheAudios(cached)
                } else {
                    // показать полосу загрузки — TODO
                }
            }
        }

        @Composable
        override fun HandlePlayVerse() {
            val verse = uiData.audioState().verseAudioFile()
            val restart = uiData.surahDetailState().audioPlayerState().restartAudio()
            LaunchedEffect(verse, restart) {
                if (verse !is VerseAudio.Empty || restart == true) {
                    surahPlayerVm.onPlayVerse(verse)
                }
            }
        }

        @Composable
        override fun HandleReciter() {
            val reciter = uiData.surahDetailState().reciterState().currentReciter()
            val reciterName = uiData.surahDetailState().reciterState().currentReciterName()
            val savedSurahNumber =
                deps.surahPlayerViewModel().getLastPlayedSurah().takeIf { it != 0 } ?: chapterNumber
            LaunchedEffect(reciter, reciterName) {
                //surahPlayerVm.downloadToCache(savedSurahNumber, reciter)
            }
        }

        @Composable
        override fun HandleAyah() {
            LaunchedEffect(uiData.surahDetailState().audioPlayerState().currentAyah()) {
                surahPlayerVm.setLastPlayedAyah(
                    uiData.surahDetailState().audioPlayerState().currentAyah()
                )
            }
        }

        @Composable
        override fun HandleInitialLoad(pageNumber: Int) {
            surahPlayerVm.setSurahNavigationCallback(
                object : SurahNavigationCallback {
                    override fun navigate(surahNumber: Int) {
                        Log.i("bug", "naviate CALLED")
                        Log.i("bug", "naviate surahNumber $surahNumber")
                        //val currentSurahNumber = uiData.surahDetailState().textState().currentSurahNumber()
                        val surahName =
                            uiData.textState().chapters()[surahNumber - 1].englishName()
                        surahPlayerVm.setAudioSurahName(surahName)
                        quranTextVm.setLastReadSurah(surahNumber)
                        surahPlayerVm.setLastPlayedSurah(surahNumber)
                        controller.navigate(
                            Screen.SurahDetail.createRoute(
                                surahNumber, surahName
                            )
                        ) {
                            // Удаляем предыдущий SurahDetail из стека, чтобы не возвращаться к нему
                            popUpTo(Screen.SurahDetail.route) {
                                inclusive = true // полностью убрать предыдущий SurahDetail
                            }
                            launchSingleTop = true // чтобы не дублировать в стеке одинаковые экраны
                        }
                    }
                }
            )

            LaunchedEffect(chapterNumber) {
                val reciter =
                    surahPlayerVm.getReciter() ?: throw IllegalStateException("Нету ресайтера")
                val reciterName = surahPlayerVm.getReciterName()
                    ?: throw IllegalStateException("Нету ресайтера name")
                surahDetailVm.selectedReciter(reciter, reciterName)
                if (reciter.isNullOrEmpty()) {
                    surahDetailVm.showReciterDialog(true)
                }

                // Запросы с безопасным фоновым выполнением
                withContext(Dispatchers.IO) {
                    quranPageVm.getUthmaniPage(pageNumber)
                    quranPageVm.getTranslatedPage(pageNumber, "ru.kuliev")
                    quranTextVm.getAllChapters()
                    quranTextVm.getArabicChapter(chapterNumber)
                    quranTranslationVm.getTranslationForChapter(chapterNumber, "ru.kuliev")/*quranAudio.downloadToCache(chapterNumber, reciter)
                    quranAudio.getChaptersAudioOfReciter(chapterNumber, reciter)*/
                }
            }
        }

        @Composable
        override fun HandleLifecycleLogging(lifecycleOwner: androidx.lifecycle.LifecycleOwner) {/*DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    Log.i("SurahDetailScreen", "SurahDetailScreenContent Lifecycle event: $event")
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    Log.i("SurahDetailScreen", "SurahDetailScreenContent disposed")
                    quranAudio.clear()
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }*/
        }
    }
}
