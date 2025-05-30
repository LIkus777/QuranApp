package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.domain.al_quran_cloud.models.audiofile.VerseAudio
import com.zaur.features.surah.base.AudioPlayer
import com.zaur.features.surah.manager.SurahDetailStateManager
import com.zaur.features.surah.screen.surah_detail.SurahNavigationCallback
import com.zaur.features.surah.screen.surah_detail.player.PlayerCommand.PauseCommand
import com.zaur.features.surah.screen.surah_detail.player.PlayerCommand.PlayWholeChapterCommand
import com.zaur.features.surah.screen.surah_detail.player.PlayerCommand.ResumeCommand


/**
 * @author Zaur
 * @since 22.05.2025
 */

interface PlaybackController {

    fun stop()
    fun pause()
    fun toggleChapterPlayback()
    fun playAtIndex(index: Int)
    fun playVerse(verse: VerseAudio)
    fun handleTrackEnd(nextIndex: Int, atEnd: Boolean)
    fun playSingle(ayahNumber: Int, surahNumber: Int, callback: (Int) -> Unit)
    fun switchSurah(newSurahNumber: Int)
    fun playRelativeAyah(offset: Int)

    fun setSurahNavigationCallback(callback: SurahNavigationCallback)

    class Base(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
        private val playlistManager: PlaylistManager,
        private val audioPlaybackHelper: AudioPlaybackHelper,
        private val surahDetailStateManager: SurahDetailStateManager, // Менеджер состояния плеера
    ) : PlaybackController {

        private val state = surahDetailStateManager.surahDetailState()

        private var surahNavigationCallback: SurahNavigationCallback? = null

        override fun switchSurah(newSurahNumber: Int) {
            audioPlayer.clearItems()
            playlistManager.clear()
            audioPlayerStateUpdater.setPlayWholeChapter(false)
            audioPlayerStateUpdater.setPlaying(false)
            audioPlayerStateUpdater.setCurrentAyahAndSurah(newSurahNumber, 1)
            surahNavigationCallback?.navigate(newSurahNumber)
        }

        override fun playRelativeAyah(offset: Int) {
            val currentIndex = state.value.audioPlayerState().currentAyah() - 1
            val targetIndex = currentIndex + offset
            if (targetIndex in playlistManager.currentPlaylist().indices) {
                playAtIndex(targetIndex)
            }
        }

        override fun playAtIndex(index: Int) {
            val playlist = playlistManager.currentPlaylist()
            if (index in playlist.indices) {
                audioPlayerStateUpdater.setCurrentAyahAndSurah(
                    surah = state.value.audioPlayerState().currentSurahNumber(), ayah = index
                )
                audioPlayerStateUpdater.markWholeChapterPlaying(
                    isAudioPlaying = true, playWholeChapter = true
                )
                audioPlayer.seekTo(playlist, index)
            }
        }

        override fun playVerse(verse: VerseAudio) {
            if (state.value.audioPlayerState().restartAudio()) {
                audioPlayer.restartAudio()
            } else {
                audioPlaybackHelper.play(verse)
            }
            audioPlayerStateUpdater.setRestartAudio(false, true)
        }

        override fun toggleChapterPlayback() {
            val items = playlistManager.currentPlaylist()
            if (items.isEmpty()) return

            val stateValue = state.value.audioPlayerState()
            val currentIndex = stateValue.currentAyah() - 1

            val cmd = when {
                !audioPlayer.isPlaying() && !audioPlayer.isPaused() -> PlayWholeChapterCommand(
                    audioPlayer, audioPlayerStateUpdater, items, startIndex = currentIndex
                )

                audioPlayer.isPlaying() -> PauseCommand(audioPlayer, audioPlayerStateUpdater)
                else -> ResumeCommand(audioPlayer, audioPlayerStateUpdater)
            }
            cmd.execute()
        }

        override fun playSingle(ayahNumber: Int, surahNumber: Int, callback: (Int) -> Unit) {
            audioPlayerStateUpdater.setCurrentAyahAndSurah(surahNumber, ayahNumber)
            audioPlayerStateUpdater.markWholeChapterPlaying(
                isAudioPlaying = true, playWholeChapter = false
            )
            callback(ayahNumber)
        }

        // убираем кэширование списка в локальной переменной
        override fun handleTrackEnd(nextIndex: Int, atEnd: Boolean) {
            if (atEnd) {
                audioPlayerStateUpdater.markWholeChapterPlaying(false, true)
                return
            }
            // вместо копипасты — просто запускаем playAtIndex:
            playAtIndex(nextIndex)
        }

        override fun pause() {
            audioPlayer.pauseAudio()
            audioPlayerStateUpdater.stop()
        }

        override fun stop() {
            audioPlayer.stopAudio()
            audioPlayerStateUpdater.stop()
        }

        override fun setSurahNavigationCallback(callback: SurahNavigationCallback) {
            this.surahNavigationCallback = callback
        }
    }

}