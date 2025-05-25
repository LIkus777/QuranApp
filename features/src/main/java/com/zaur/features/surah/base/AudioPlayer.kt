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
    fun restartAudio()
    fun playFromIndex(items: List<MediaItem>, index: Int, positionMs: Long)
    fun seekTo(items: List<MediaItem>, index: Int, positionMs: Long = 0L)
    fun seekTo(position: Long)

    fun playAudio(url: String, playFromCache: Boolean = false) // Воспроизвести аудио по URL

    fun playPlaylist(items: List<MediaItem>)

    fun setProgressListener(listener: (currentPosition: Long, duration: Long) -> Unit)

    fun setAudioPlayerCallback(callback: AudioPlayerCallback)

    // Реализация плеера, использующего ExoPlayer
    data class Base(private val context: Context) : AudioPlayer {
        private var player: ExoPlayer? = null
        private var currentMediaItem: MediaItem? = null

        private var audioPlayerCallback: AudioPlayerCallback? = null

        private var updateHandler: Handler? = null
        private var updateRunnable: Runnable? = null
        private var progressListener: ((Long, Long) -> Unit)? = null

        override fun setProgressListener(listener: (currentPosition: Long, duration: Long) -> Unit) {
            progressListener = listener
        }

        init {
            Log.i("TAG", "SurahPlayer: player $player")
            player = ExoPlayer.Builder(context).build().apply {
                setAudioAttributes(
                    AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
                        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build(), true
                )
                setHandleAudioBecomingNoisy(true)

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        Log.d("TAG", "onPlaybackStateChanged: $state CHANGED")
                        if (state == Player.STATE_ENDED) {
                            audioPlayerCallback?.audioEnded()
                        }
                    }

                    // ВОТ ТУТ: вызывается каждый раз, когда проигрыватель переходит к следующему аяту
                    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                        val mediaId = mediaItem?.mediaId
                        Log.d("TAG", "onMediaItemTransition: mediaId $mediaId")
                        audioPlayerCallback?.onAyahChanged(mediaId)
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        Log.e("TAGGG", "Playback error: ${error.errorCodeName} | ${error.message}")
                    }
                })
            }
            // Отслеживаем прогресс проигрывания
            player?.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) {
                        startProgressUpdates()
                    }
                }

                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED || state == Player.STATE_IDLE) {
                        stopProgressUpdates()
                    }
                }
            })
        }

        fun startProgressUpdates() {
            if (updateHandler == null) {
                updateHandler = Handler(Looper.getMainLooper())
            }

            updateRunnable = object : Runnable {
                override fun run() {
                    val player = player ?: return
                    progressListener?.invoke(player.currentPosition, player.duration)
                    updateHandler?.postDelayed(this, 16L)
                }
            }
            updateHandler?.post(updateRunnable!!)
        }

        fun stopProgressUpdates() {
            updateRunnable?.let { updateHandler?.removeCallbacks(it) }
        }

        override fun resume() {
            player?.play()
        }

        override fun playPlaylist(items: List<MediaItem>) {
            items.forEach {
                Log.d("TAG", "playPlaylist: $it")
            }
            player?.apply {
                clearMediaItems()
                addMediaItems(items)
                prepare()
                playWhenReady = true
            }
        }

        override fun playAudio(url: String, playFromCache: Boolean) {
            if (url.isEmpty()) {
                player?.play() // просто возобновляем
                return
            }

            val uri = if (url.startsWith("http") && playFromCache == false) {
                Uri.parse(url)
            } else {
                Uri.fromFile(File(url))
            }

            val mediaItem = MediaItem.fromUri(uri)

            if (currentMediaItem?.localConfiguration?.uri != mediaItem.localConfiguration?.uri) {
                player?.setMediaItem(mediaItem)
                player?.prepare()
                player?.playWhenReady = true
            }

            player?.play()
            currentMediaItem = mediaItem
        }

        override fun pauseAudio() {
            Log.i("TAG", "pauseAudio: PAUSE}")
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

        override fun playFromIndex(
            items: List<MediaItem>,
            index: Int,
            positionMs: Long,
        ) {
            player?.apply {
                // 1) Сбрасываем всё
                clearMediaItems()
                // 2) Добавляем все items
                addMediaItems(items)
                // 3) Устанавливаем стартовый трек и позицию
                //    -- здесь важно вызвать именно setMediaItems с нужным индексом+позицией
                setMediaItems(items, index, positionMs)
                // 4) Подготовка и запуск
                prepare()
                playWhenReady = true
            }
        }

        override fun seekTo(items: List<MediaItem>, index: Int, positionMs: Long) {
            player?.let {
                it.setMediaItems(items, index, 0L)
                it.playWhenReady = true
            }
        }

        override fun seekTo(position: Long) {
            player?.seekTo(position)
        }

        override fun release() {
            player?.release()
        }

        override fun isPlaying(): Boolean {
            Log.i("TAG", "isPlaying: ${player?.isPlaying == true}")
            return player?.isPlaying == true
        }

        override fun isPaused(): Boolean =
            !player?.isPlaying!! && player?.playbackState == Player.STATE_READY

        override fun clear() {
            player?.stop()
            player?.release()
            player = null
            currentMediaItem = null
            audioPlayerCallback = null
        }

        override fun setAudioPlayerCallback(callback: AudioPlayerCallback) {
            this.audioPlayerCallback = callback
        }
    }
}