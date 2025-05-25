package com.zaur.features.surah.screen.surah_detail.player

import androidx.media3.common.MediaItem
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
        private val mediaItems: List<MediaItem>,
        private val startIndex: Int
    ) : PlayerCommand {
        override fun execute() {
            // вместо playPlaylist → play from startIndex
            audioPlayer.playFromIndex(mediaItems, startIndex, /*positionMs=*/0L)
            audioPlayerStateUpdater.markWholeChapterPlaying(
                isAudioPlaying = true,
                playWholeChapter = true
            )
        }
    }

    class PauseCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
    ) : PlayerCommand {
        override fun execute() {
            audioPlayer.pauseAudio()
            audioPlayerStateUpdater.setPlaying(false)
        }
    }

    class ResumeCommand(
        private val audioPlayer: AudioPlayer,
        private val audioPlayerStateUpdater: AudioPlayerStateUpdater,
    ) : PlayerCommand {
        override fun execute() {
            audioPlayer.resume()
            audioPlayerStateUpdater.setPlaying(true)
        }
    }

}