package com.zaur.features.surah.base

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import java.io.File

/**
 * @author Zaur
 * @since 2025-05-12
 */

interface AudioPlayerCallback {
    fun audioEnded()
    fun onAyahChanged(mediaId: String?)
}

// Интерфейс для всех операций с аудио
interface AudioPlayer {

    fun clear()
    fun resume()
    fun release() // Освобождение ресурсов
    fun isPaused(): Boolean
    fun stopAudio() // Остановка аудио
    fun isPlaying(): Boolean // Проверка, воспроизводится ли аудио
    fun pauseAudio() // Пауза аудио
    fun clearItems()
    fun restartAudio()
    fun playFromIndex(items: List<MediaItem>, index: Int, positionMs: Long)
    fun seekTo(items: List<MediaItem>, index: Int, positionMs: Long = 0L)
    fun seekTo(position: Long)

    fun playAudio(url: String, playFromCache: Boolean = false) // Воспроизвести аудио по URL

    fun setProgressListener(listener: (currentPosition: Long, duration: Long) -> Unit)

    fun setAudioPlayerCallback(callback: AudioPlayerCallback)

    // Реализация плеера, использующего ExoPlayer
    data class Base(private val context: Context) : AudioPlayer {

        private val player: ExoPlayer = ExoPlayer.Builder(context).build()
        private var currentMediaItem: MediaItem? = null
        private var audioPlayerCallback: AudioPlayerCallback? = null
        private var progressListener: ((Long, Long) -> Unit)? = null

        private val handler = Handler(Looper.getMainLooper())
        private val progressUpdateInterval = 16L
        private val progressRunnable = object : Runnable {
            override fun run() {
                player.takeIf { it.isPlaying }?.let {
                    progressListener?.invoke(it.currentPosition, it.duration)
                    handler.postDelayed(this, progressUpdateInterval)
                }
            }
        }

        init {
            player.setAudioAttributes(
                AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(), true
            )
            player.setHandleAudioBecomingNoisy(true)

            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    when (state) {
                        Player.STATE_ENDED -> audioPlayerCallback?.audioEnded()
                        Player.STATE_IDLE, Player.STATE_ENDED -> stopProgressUpdates()
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) startProgressUpdates() else stopProgressUpdates()
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    audioPlayerCallback?.onAyahChanged(mediaItem?.mediaId)
                }

                override fun onPlayerError(error: PlaybackException) {
                    Log.e(
                        "AudioPlayer", "Playback error: ${error.errorCodeName} | ${error.message}"
                    )
                }
            })
        }

        fun startProgressUpdates() {
            handler.removeCallbacks(progressRunnable)
            handler.post(progressRunnable)
        }

        fun stopProgressUpdates() {
            handler.removeCallbacks(progressRunnable)
        }

        override fun playAudio(url: String, playFromCache: Boolean) {
            if (url.isEmpty()) {
                player.play()
                return
            }

            val uri =
                if (url.startsWith("http") && !playFromCache) Uri.parse(url) else Uri.fromFile(
                    File(url)
                )
            val mediaItem = MediaItem.fromUri(uri)

            if (currentMediaItem?.localConfiguration?.uri != mediaItem.localConfiguration?.uri) {
                player.setMediaItem(mediaItem)
                player.prepare()
                player.playWhenReady = true
                currentMediaItem = mediaItem
            } else {
                player.play()
            }
        }

        override fun playFromIndex(items: List<MediaItem>, index: Int, positionMs: Long) {
            player.apply {
                clearMediaItems()
                addMediaItems(items)
                setMediaItems(items, index, positionMs)
                prepare()
                playWhenReady = true
            }
        }

        override fun seekTo(items: List<MediaItem>, index: Int, positionMs: Long) {
            player.apply {
                setMediaItems(items, index, positionMs)
                playWhenReady = true
            }
        }

        override fun seekTo(position: Long) = player.seekTo(position)

        override fun resume() = player.play()
        override fun release() {
            player.release()
        }

        override fun pauseAudio() = player.pause()

        override fun stopAudio() = player.stop()

        override fun clearItems() {
            player.stop()
            player.seekTo(0)
            player.clearMediaItems()
        }

        override fun restartAudio() {
            currentMediaItem?.let {
                player.setMediaItem(it)
                player.prepare()
                player.seekTo(0)
                player.playWhenReady = true
            }
        }

        override fun isPlaying() = player.isPlaying

        override fun isPaused() = !player.isPlaying && player.playbackState == Player.STATE_READY

        override fun clear() {
            stopProgressUpdates()
            player.release()
            audioPlayerCallback = null
            currentMediaItem = null
        }

        override fun setProgressListener(listener: (Long, Long) -> Unit) {
            progressListener = listener
        }

        override fun setAudioPlayerCallback(callback: AudioPlayerCallback) {
            audioPlayerCallback = callback
        }
    }
}