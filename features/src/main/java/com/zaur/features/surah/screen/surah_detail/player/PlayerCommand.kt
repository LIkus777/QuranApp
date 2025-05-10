package com.zaur.features.surah.screen.surah_detail.player

import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.features.surah.base.AudioPlayer

interface PlayerCommand {
    fun execute()

    class PlayWholeChapterCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
        private val playlistBuilder: PlaylistBuilder,
        private val ayahList: AyahList,
        private val cacheAyahList: CacheAyahList,
        private val isOfflineMode: Boolean,
        private val currentSurahNumber: Int
    ) : PlayerCommand {
        override fun execute() {
            val playlist = if (isOfflineMode) {
                playlistBuilder.buildLocalPlaylist(ayahList, currentSurahNumber)
            } else {
                playlistBuilder.buildCachePlaylist(cacheAyahList)
            }

            audioPlayer.playPlaylist(playlist)
            audioPlayerStateUpdater.markWholeChapterPlaying(isAudioPlaying = true, playWholeChapter = true)
        }
    }

    class PauseCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater
    ) : PlayerCommand {
        override fun execute() {
            audioPlayer.pauseAudio()
            audioPlayerStateUpdater.setPlaying(false)
        }
    }

    class ResumeCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater
    ) : PlayerCommand {
        override fun execute() {
            audioPlayer.resume()
            audioPlayerStateUpdater.setPlaying(true)
        }
    }

}