package com.zaur.features.surah.viewmodel.handlers

import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.domain.al_quran_cloud.use_case.QuranAudioUseCase
import com.zaur.features.surah.manager.ReciterManager
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.SurahPlayerObservable
import com.zaur.features.surah.screen.surah_detail.player.SurahPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @author Zaur
 * @since 25.05.2025
 */

interface AudioResultHandler {

    fun handleVerseAudio(data: VerseAudio)
    fun handleChapterAudio(data: ChapterAudioFile)
    fun handleCacheAudio(data: List<CacheAudio.Base>)

    class Base(
        private val scope: CoroutineScope,
        private val surahPlayer: SurahPlayer,
        private val reciterManager: ReciterManager,
        private val stateManager: SurahDetailStateManager,
        private val observable: SurahPlayerObservable.Mutable,
    ) : AudioResultHandler {
        override fun handleVerseAudio(data: VerseAudio) {
            val currentAudio = observable.audioState().value.verseAudioFile().audio()
            val updatedState = if (data.audio() == currentAudio) {
                stateManager.surahDetailState().value.copy(
                    audioPlayerState = stateManager.surahDetailState().value.audioPlayerState()
                        .copy(restartAudio = true)
                )
            } else null

            scope.launch(Dispatchers.Main) {
                updatedState?.let { stateManager.updateState(it) }
                    ?: observable.update(observable.audioState().value.copy(verseAudioFile = data))
            }
        }

        override fun handleChapterAudio(data: ChapterAudioFile) {
            scope.launch(Dispatchers.Main) {
                // обновляем UI-состояние
                observable.update(observable.audioState().value.copy(chaptersAudioFile = data))

                // сразу ставим новый плейлист из data, а не из observable
                // получаем текущего reciter из useCase или ViewModel
                val reciterId = reciterManager.getReciter() ?: ""

                // маппинг — гарантируем, что reciter не будет null
                val ayahs = data.ayahs().map { ay ->
                    Ayah.Base(
                        reciterId,                      // вместо ay.reciter()
                        ay.verseNumber(),
                        ay.audio(),
                        ay.audioSecondary(),
                        ay.text(),
                        ay.numberInSurah(),
                        ay.juz(),
                        ay.manzil(),
                        ay.page(),
                        ay.ruku(),
                        ay.hizbQuarter(),
                        ay.sajda()
                    )
                }

                surahPlayer.setAyahs(ayahs)
            }
        }

        override fun handleCacheAudio(data: List<CacheAudio.Base>) {
            scope.launch(Dispatchers.Main) {
                observable.update(observable.audioState().value.copy(cacheAudios = data))
            }
        }
    }
}