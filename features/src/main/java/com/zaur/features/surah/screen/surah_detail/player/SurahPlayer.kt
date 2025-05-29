package com.zaur.features.surah.screen.surah_detail.player

import android.util.Log
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.base.AudioPlayerCallback
import com.zaur.features.surah.manager.SurahDetailStateManager
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
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : SurahPlayer {

        private var surahPlayerVmCallback: SurahPlayerViewModel.SurahPlayerVmCallback? = null

        private val state = surahDetailStateManager.surahDetailState()
        private val playlistManager = PlaylistManager.Base(playlistBuilder, surahDetailStateManager)
        private val playbackController = PlaybackController.Base(
            audioPlayer,
            audioPlayerStateUpdater,
            playlistManager,
            audioPlaybackHelper,
            surahDetailStateManager
        )

        init {
            audioPlayer.setAudioPlayerCallback(object : AudioPlayerCallback {
                override fun audioEnded() {
                    // если режим — НЕ воспроизведение всей суры, сразу останавливаем
                    if (!state.value.audioPlayerState().playWholeChapter()) {
                        Log.i("TAG", "audioEnded: IF")
                        audioPlayerStateUpdater.stop()
                        return
                    }

                    // иначе — логика перехода к следующему аяту
                    val next = state.value.audioPlayerState().currentAyah() + 1
                    val atEnd = next > playlistManager.currentPlaylist().lastIndex

                    playbackController.handleTrackEnd(next, atEnd)

                    if (!atEnd) {
                        surahPlayerVmCallback?.callVerseAudioFile(next)
                    }
                }

                override fun onAyahChanged(mediaId: String?) {
                    mediaId?.toIntOrNull()?.let { num ->
                        audioPlayerStateUpdater.updateCurrentAyah(num)
                    }
                }
            })

            audioPlayer.setProgressListener { pos, dur ->
                // pos — текущая позиция в мс
                // dur — общая длительность (в мс), может быть ненадёжной (<=0)
                val safeDur = if (dur <= 0L) 1L else dur
                surahDetailStateManager.updatePlaybackPosition(pos, safeDur)
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
            (state.value.audioPlayerState().currentSurahNumber() + 1).coerceAtLeast(1)
        )

        override fun onPreviousSurahClicked() = playbackController.switchSurah(
            (state.value.audioPlayerState().currentSurahNumber() - 1).coerceAtLeast(1)
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
        }

        override fun setSurahNavigationCallback(callback: SurahNavigationCallback) {
            playbackController.setSurahNavigationCallback(callback)
        }
    }
}