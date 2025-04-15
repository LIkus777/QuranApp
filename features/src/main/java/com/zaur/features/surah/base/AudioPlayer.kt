package com.zaur.features.surah.base

import android.content.Context
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

interface AudioPlayerCallback {
    fun audioEnded()
}

// Интерфейс для всех операций с аудио
interface AudioPlayer {
    fun playAudio(url: String) // Воспроизвести аудио по URL
    fun pauseAudio() // Пауза аудио
    fun stopAudio() // Остановка аудио
    fun restartAudio()
    fun release() // Освобождение ресурсов
    fun isPlaying(): Boolean // Проверка, воспроизводится ли аудио
    fun isPaused(): Boolean

    fun clear()

    fun setAudioPlayerCallback(callback: AudioPlayerCallback)

    // Реализация плеера, использующего ExoPlayer
    class Base(private val context: Context) : AudioPlayer {
        private var player: ExoPlayer? = null
        private var currentMediaItem: MediaItem? = null

        private var audioPlayerCallback: AudioPlayerCallback? = null

        init {
            player = ExoPlayer.Builder(context).build().apply {
                setAudioAttributes(
                    AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(), true
                )
                setHandleAudioBecomingNoisy(true)

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == ExoPlayer.STATE_ENDED) {
                            audioPlayerCallback?.audioEnded()
                        }
                    }
                })

                playWhenReady = true
            }
        }

        override fun playAudio(url: String) {
            if (url.isNotEmpty()) {
                val mediaItem = MediaItem.fromUri(url)

                // Сравниваем текущий mediaItem с новым, проверяя их URI
                if (currentMediaItem?.localConfiguration?.uri != mediaItem.localConfiguration?.uri) {
                    player?.setMediaItem(mediaItem)
                    player?.prepare()
                }

                player?.play()
                currentMediaItem = mediaItem
            } else {
                player?.play()
            }
        }

        override fun pauseAudio() {
            player?.pause()
        }

        override fun stopAudio() {
            player?.stop()
        }

        override fun restartAudio() {
            player?.let {
                if (currentMediaItem != null) {
                    it.setMediaItem(currentMediaItem!!)
                    it.prepare()
                    it.seekTo(0) // Перемотка на начало
                    it.playWhenReady = true
                }
            }
        }

        override fun release() {
            player?.release()
        }

        override fun isPlaying(): Boolean {
            return player?.isPlaying == true
        }

        override fun isPaused(): Boolean = !player?.isPlaying!! && player?.playbackState == Player.STATE_READY

        override fun clear() {
            player?.stop()
            player?.release()
            player = null
            currentMediaItem = null
            audioPlayerCallback = null
        }

        override fun setAudioPlayerCallback(callback: AudioPlayerCallback)  {
            this.audioPlayerCallback = callback
        }
    }
}
