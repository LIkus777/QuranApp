package com.zaur.features.surah.viewmodel.handlers

import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.ChapterAudioFile
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudioAqc
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.observables.QuranAudioObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @author Zaur
 * @since 25.05.2025
 */

interface AudioResultHandler {

    fun handleVerseAudio(data: VerseAudioAqc)
    fun handleChapterAudio(data: ChapterAudioFile)
    fun handleCacheAudio(data: List<CacheAudio.Base>)

    class Base(
        private val observable: QuranAudioObservable.Mutable,
        private val stateManager: SurahDetailStateManager,
        private val scope: CoroutineScope,
    ) : AudioResultHandler {
        override fun handleVerseAudio(data: VerseAudioAqc) {
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
                // 1) Обновляем UI
                observable.update(observable.audioState().value.copy(chaptersAudioFile = data))
                // 2) И перезагружаем плейлист
                //    Предположим, data.audios: List<VerseAudioAqc> или подобное
                //    Приводим к списку Ayah.Base и CacheAudio.Base, как у вас устроено
                /*val ayahs: List<Ayah.Base> = ... // извлечь из data
                surahPlayer.setAyahs(ayahs)*/
                // (аудиоплейер сам потом готов)
            }
        }

        override fun handleCacheAudio(data: List<CacheAudio.Base>) {
            scope.launch(Dispatchers.Main) {
                observable.update(observable.audioState().value.copy(cacheAudios = data))
            }
        }
    }
}