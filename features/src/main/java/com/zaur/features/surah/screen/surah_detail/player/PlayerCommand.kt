package com.zaur.features.surah.screen.surah_detail.player

import android.util.Log
import androidx.media3.common.MediaItem
import com.zaur.domain.al_quran_cloud.models.audiofile.Ayah
import com.zaur.domain.al_quran_cloud.models.audiofile.CacheAudio
import com.zaur.features.surah.base.AudioPlayer

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface PlayerCommand {
    fun execute()

    class PlayWholeChapterCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
        private val playlistBuilder: PlaylistBuilder,
        private val mediaItem: List<MediaItem>,
    ) : PlayerCommand {
        override fun execute() {
            Log.i("TAG", "execute: PlayWholeChapterCommand")
            Log.i("TAG", "execute: $audioPlayer")
            Log.i("TAG", "execute: $audioPlayerStateUpdater")
            Log.i("TAG", "execute: $playlistBuilder")
            audioPlayer.playPlaylist(mediaItem)
            audioPlayerStateUpdater.markWholeChapterPlaying(
                isAudioPlaying = true,
                playWholeChapter = true
            )
        }
    }

    class PauseCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater
    ) : PlayerCommand {
        override fun execute() {
            Log.i("TAG", "execute: PauseCommand")
            audioPlayer.pauseAudio()
            audioPlayerStateUpdater.setPlaying(false)
        }
    }

    class ResumeCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater
    ) : PlayerCommand {
        override fun execute() {
            Log.i("TAG", "execute: ResumeCommand")
            audioPlayer.resume()
            audioPlayerStateUpdater.setPlaying(true)
        }
    }

}