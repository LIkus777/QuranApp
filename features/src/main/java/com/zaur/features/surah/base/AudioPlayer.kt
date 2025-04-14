package com.zaur.features.surah.base

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

// Интерфейс для всех операций с аудио
interface AudioPlayer {
    fun playAudio(url: String) // Воспроизвести аудио по URL
    fun pauseAudio() // Пауза аудио
    fun stopAudio() // Остановка аудио
    fun release() // Освобождение ресурсов
    fun isPlaying(): Boolean // Проверка, воспроизводится ли аудио

    // Реализация плеера, использующего ExoPlayer
    class Base(private val context: Context) : AudioPlayer {
        private var player: ExoPlayer? = null
        private var currentMediaItem: MediaItem? = null

        init {
            player = ExoPlayer.Builder(context).build()
        }

        override fun playAudio(url: String) {
            val mediaItem = MediaItem.fromUri(url)

            // Сравниваем текущий mediaItem с новым, проверяя их URI
            if (currentMediaItem?.localConfiguration?.uri != mediaItem.localConfiguration?.uri) {
                player?.setMediaItem(mediaItem)
                player?.prepare()
            }

            player?.play()
            currentMediaItem = mediaItem
        }

        override fun pauseAudio() {
            player?.pause()
        }

        override fun stopAudio() {
            player?.stop()
        }

        override fun release() {
            player?.release()
        }

        override fun isPlaying(): Boolean {
            return player?.isPlaying == true
        }
    }
}
