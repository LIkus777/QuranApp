package com.zaur.features.surah.screen.surah_detail.player

import android.util.Log
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.base.AudioPlayerCallback
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.manager.SurahPlayerStateManager
import com.zaur.features.surah.screen.surah_detail.SurahNavigationCallback
import com.zaur.features.surah.viewmodel.SurahPlayerViewModel

/**
 * @author Zaur
 * @since 2025-05-12
 */

// Интерфейс для управления плеером
interface SurahPlayer {

    fun onNextAyahClicked()
    fun onNextSurahClicked()
    fun onPreviousAyahClicked()
    fun onPreviousSurahClicked()

    fun seekTo(position: Long)
    fun onPlayVerse(verse: VerseAudio)
    fun onPlayWholeClicked()
    fun onPlayWholeClickedForNewSurah()
    fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int)
    fun onPauseClicked()
    fun onStopClicked()

    fun clear()

    fun setQuranAudioVmCallback(callback: SurahPlayerViewModel.SurahPlayerVmCallback)
    fun setSurahNavigationCallback(callback: SurahNavigationCallback)

    suspend fun setAyahs(ayahs: List<Ayah.Base>)
    suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>)

    // Реализация плеера с разделением на управление состоянием и воспроизведением
    class Base(
        private val playlistBuilder: PlaylistBuilder,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
        private val audioPlaybackHelper: AudioPlaybackHelper,
        private val audioPlayer: AudioPlayer,  // Интерфейс для работы с плеером
        private val surahDetailStateManager: SurahDetailStateManager,
        private val surahPlayerStateManager: SurahPlayerStateManager,
    ) : SurahPlayer {

        private var surahPlayerVmCallback: SurahPlayerViewModel.SurahPlayerVmCallback? = null

        private val playerState = surahPlayerStateManager.surahPlayerState()

        private val playlistManager = PlaylistManager.Base(
            playlistBuilder, surahDetailStateManager, surahPlayerStateManager
        ) { newList ->
            // этот код выполнится каждый раз, когда reciter/режим дадут новый список
            val idx = playerState.value.currentAyah() - 1
            if (idx in newList.indices) {
                audioPlayer.clearItems()
                audioPlayer.playFromIndex(newList, idx, positionMs = 0L)
            }
        }

        private val playbackController = PlaybackController.Base(
            audioPlayer,
            audioPlayerStateUpdater,
            playlistManager,
            audioPlaybackHelper,
            surahPlayerStateManager,
        )

        init {
            audioPlayer.setAudioPlayerCallback(object : AudioPlayerCallback {
                override fun audioEnded() {
                    // если режим — НЕ воспроизведение всей суры, сразу останавливаем
                    if (!playerState.value.playWholeChapter()) {
                        Log.i("TAG", "audioEnded: IF")
                        audioPlayerStateUpdater.stop()
                        return
                    }

                    // иначе — логика перехода к следующему аяту
                    val next = playerState.value.currentAyah() + 1
                    val atEnd = next > playlistManager.currentPlaylist().lastIndex

                    playbackController.handleTrackEnd(next, atEnd)

                    if (!atEnd) {
                        surahPlayerVmCallback?.callVerseAudioFile(next)
                    }
                }

                override fun onAyahChanged(mediaId: String?) {
                    mediaId?.toIntOrNull()?.let { num ->
                        surahPlayerVmCallback?.saveLastPlayedAyah(num)
                        audioPlayerStateUpdater.updateCurrentAyah(num)
                    }
                }
            })

            audioPlayer.setProgressListener { pos, dur ->
                // pos — текущая позиция в мс
                // dur — общая длительность (в мс), может быть ненадёжной (<=0)
                val safeDur = if (dur <= 0L) 1L else dur
                surahPlayerStateManager.updatePlaybackPosition(pos, safeDur)
            }
        }

        override suspend fun setAyahs(ayahs: List<Ayah.Base>) {
            playlistManager.setAyahs(ayahs)
        }

        override suspend fun setCacheAudios(ayahs: List<CacheAudio.Base>) {
            playlistManager.setCacheAudios(ayahs)
        }

        override fun onNextAyahClicked() = playbackController.playRelativeAyah(1)
        override fun onPreviousAyahClicked() = playbackController.playRelativeAyah(-1)

        override fun onNextSurahClicked() = playbackController.switchSurah(
            (playerState.value.currentSurahNumber() + 1).coerceAtLeast(1)
        )

        override fun onPreviousSurahClicked() = playbackController.switchSurah(
            (playerState.value.currentSurahNumber() - 1).coerceAtLeast(1)
        )

        override fun seekTo(position: Long) {
            audioPlayer.seekTo(position)
        }


        override fun onPlayVerse(verse: VerseAudio) {
            playbackController.playVerse(verse)
        }

        override fun onPlayWholeClicked() {
            playbackController.toggleChapterPlayback()
        }

        override fun onPlayWholeClickedForNewSurah() {
            playbackController.toggleChapterPlaybackForNewSurah()
        }

        override fun onPlaySingleClicked(ayahNumber: Int, surahNumber: Int) {
            playbackController.playSingle(ayahNumber, surahNumber) {
                surahPlayerVmCallback?.callVerseAudioFile(it)
            }
        }

        override fun onPauseClicked() {
            playbackController.pause()
        }

        override fun onStopClicked() {
            playbackController.stop()
        }

        override fun clear() {
            audioPlayer.clear()
            playlistManager.clear()
            surahPlayerVmCallback = null
        }

        override fun setQuranAudioVmCallback(callback: SurahPlayerViewModel.SurahPlayerVmCallback) {
            this.surahPlayerVmCallback = callback
            playbackController.setQuranAudioVmCallback(this.surahPlayerVmCallback!!)
        }

        override fun setSurahNavigationCallback(callback: SurahNavigationCallback) {
            playbackController.setSurahNavigationCallback(callback)
        }
    }
}