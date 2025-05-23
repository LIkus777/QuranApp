package com.zaur.features.surah.base

import android.content.Context
import android.net.Uri
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
    fun seekTo(items: List<MediaItem>, index: Int, positionMs: Long = 0L)

    fun playAudio(url: String, playFromCache: Boolean = false) // Воспроизвести аудио по URL

    fun playPlaylist(items: List<MediaItem>)

    fun setAudioPlayerCallback(callback: AudioPlayerCallback)

    // Реализация плеера, использующего ExoPlayer
    data class Base(private val context: Context) : AudioPlayer {
        private var player: ExoPlayer? = null
        private var currentMediaItem: MediaItem? = null

        private var audioPlayerCallback: AudioPlayerCallback? = null

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

        override fun seekTo(items: List<MediaItem>, index: Int, positionMs: Long) {
            player?.let {
                it.setMediaItems(items, index, 0L)
                it.playWhenReady = true
            }
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